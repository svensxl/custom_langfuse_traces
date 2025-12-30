# project.md

name: custom-langfuse-traces
owner: Sven Sun
language: Java 21
build_tool: Maven
status: planning
visibility: internal

## goal

提供一个 Java 模块，用于快速生成符合 Langfuse 格式的 trace 和 observation 数据，支持以多种方式（Kafka、本地文件）输出，用于追踪大模型 Agent 的执行链路。

## problem

现有 Langfuse Java SDK 在初始化时必须绑定固定的 project，对象较重，不支持在创建 span（observation）时灵活指定 project id，无法满足用户的多项目 trace 需求。  
因此，需要自研一个轻量化的 trace 生成模块，支持用户手动创建和上报符合 Langfuse 规范的 trace 数据。

## background

Langfuse trace 包含两类核心对象：

- **trace**：代表一次完整的请求或操作，例如从用户输入到模型响应的完整交互。trace 记录请求的整体输入、输出、用户、会话、标签等上下文信息。  
  每条 trace 会被存储到独立的 ClickHouse `trace` 表中，用于后续查询、检索和分析。
- **observation**：代表 trace 内部的单个执行步骤（类似 OpenTelemetry 的 span），记录子过程的执行情况。

参考资料：

- [Observation Types](https://langfuse.com/docs/observability/features/observation-types)
- [Integrations with OpenTelemetry](https://langfuse.com/integrations/native/opentelemetry)

## core_design

- **语言与构建**：Java 21 + Maven
- **依赖**：Kafka Client（支持 Kafka 2.0 集群）
- **初始化机制**：
  - 支持设置通用 trace 属性（如环境、版本、发布号）
  - 支持配置上报方式（Kafka、本地文件）
- **核心功能**：
  - 快速创建 trace / observation
  - 支持自定义 trace_id、observation_id、project_id
  - 若用户未指定 id，模块自动生成（提供 ID 生成工具类）
  - 提供 trace context 对象，用于跨方法传递上下文（避免 ThreadLocal）
- **Reporter 多态扩展设计**：
  - 定义统一的 `Reporter` 抽象接口（如 `TraceReporter`）
  - 提供多态实现：
    - `KafkaReporter`：将 trace/observation 上报到 Kafka topic
    - `FileReporter`：将 trace/observation 以 JSON 形式写入本地文件
  - 后续可扩展更多实现（如 `HttpReporter`, `ConsoleReporter` 等）
  - 所有 reporter 均支持异步上报与批量提交配置
- **数据输出**：
  - 输出 JSON 格式数据
  - Kafka 与文件输出可并行启用
- **测试要求**：
  - 每个核心功能类（如 Trace、Observation、Reporter、IdGenerator）均需有独立单元测试
  - 单元测试使用 JUnit 5 编写
  - KafkaReporter 测试使用 MockKafka 或内存替身
  - 所有测试应验证数据结构正确性、序列化结果、Reporter 行为与异常处理逻辑

## data_schema

### trace 表字段与语义说明

**trace** 通常表示一个独立的请求或操作过程，例如：

> 当用户向聊天机器人发送问题时，从用户输入到模型输出整个交互过程都会生成一条 trace。

这条 trace 记录了：

- 整体请求的输入与输出；
- 用户、会话、标签等上下文元数据；
- 关联的项目与环境信息。

trace 会被持久化存储在独立的 **ClickHouse trace 表** 中，用于后续的查询、统计和可视化分析。

| 字段名      | 类型                | 说明                          |
| ----------- | ------------------- | ----------------------------- |
| id          | String              | trace 唯一标识                |
| timestamp   | DateTime64(3)       | trace 创建时间或开始时间      |
| name        | String              | trace 名称                    |
| user_id     | Nullable(String)    | 用户 ID                       |
| metadata    | Map(String, String) | 附加元数据                    |
| release     | Nullable(String)    | 发布版本                      |
| version     | Nullable(String)    | 应用版本号                    |
| project_id  | String              | 关联项目 ID                   |
| environment | String              | 环境（默认值 'default'）      |
| public      | Bool                | 是否公开                      |
| bookmarked  | Bool                | 是否收藏                      |
| tags        | Array(String)       | 标签列表                      |
| input       | Nullable(String)    | trace 输入内容（JSON / 文本） |
| output      | Nullable(String)    | trace 输出内容（JSON / 文本） |
| session_id  | Nullable(String)    | 会话 ID                       |
| created_at  | DateTime64(3)       | 创建时间（默认 now()）        |
| updated_at  | DateTime64(3)       | 更新时间（默认 now()）        |
| event_ts    | DateTime64(3)       | 事件发生时间                  |
| is_deleted  | UInt8               | 是否被标记删除（0/1）         |

### observation 表字段

| 字段名                 | 类型                    | 说明                     |
| ---------------------- | ----------------------- | ------------------------ |
| id                     | String                  | observation 唯一标识     |
| trace_id               | String                  | 关联的 trace ID          |
| project_id             | String                  | 所属项目 ID              |
| environment            | String                  | 环境（默认值 'default'） |
| type                   | String                  | observation 类型         |
| parent_observation_id  | Nullable(String)        | 父级 observation ID      |
| start_time             | DateTime64(3)           | 开始时间                 |
| end_time               | Nullable(DateTime64(3)) | 结束时间                 |
| name                   | String                  | 名称                     |
| metadata               | Map(String, String)     | 元数据                   |
| level                  | String                  | 等级（如 info, error）   |
| status_message         | Nullable(String)        | 状态信息或错误描述       |
| version                | Nullable(String)        | 版本号                   |
| input                  | Nullable(String)        | 输入内容（JSON / 文本）  |
| output                 | Nullable(String)        | 输出内容（JSON / 文本）  |
| provided_model_name    | Nullable(String)        | 用户提供的模型名称       |
| internal_model_id      | Nullable(String)        | 内部模型 ID              |
| model_parameters       | Nullable(String)        | 模型参数（JSON 格式）    |
| provided_usage_details | Map(String, UInt64)     | 用户提供的使用统计       |
| usage_details          | Map(String, UInt64)     | 系统生成的使用统计       |
| provided_cost_details  | Map(String, Double)     | 用户提供的成本信息       |
| cost_details           | Map(String, Double)     | 系统生成的成本信息       |
| total_cost             | Nullable(Double)        | 总成本                   |
| completion_start_time  | Nullable(DateTime64(3)) | 模型生成开始时间         |
| prompt_id              | Nullable(String)        | prompt ID                |
| prompt_name            | Nullable(String)        | prompt 名称              |
| prompt_version         | Nullable(UInt16)        | prompt 版本号            |
| created_at             | DateTime64(3)           | 创建时间（默认 now()）   |
| updated_at             | DateTime64(3)           | 更新时间（默认 now()）   |
| event_ts               | DateTime64(3)           | 事件发生时间             |
| is_deleted             | UInt8                   | 是否被标记删除（0/1）    |

## deliverables

- 可直接引用的 Java 库 API（Maven artifact）
- 测试代码（JUnit + MockKafka）
- 示例 Demo（展示 Kafka 与本地文件输出）
- ID 生成工具类（TraceIdGenerator、ObservationIdGenerator）
- 项目技术文档（架构说明、类图）
- 用户使用文档（README、快速上手指南）

## timeline

| 阶段 | 目标                                                       | 时间    |
| ---- | ---------------------------------------------------------- | ------- |
| M1   | 初始化项目结构，定义数据模型与接口                         | 2026-01 |
| M2   | 实现 Trace / Observation 生成器与 Reporter 抽象            | 2026-02 |
| M3   | 实现 KafkaReporter 与 FileReporter，并完成自动 ID 生成工具 | 2026-03 |
| M4   | 添加测试与 Demo，完善文档                                  | 2026-04 |

## ai_tasks

- [ ] 创建项目目录结构（含 src/main/java、test、resources）
- [ ] 生成核心类：
  - `Trace`、`Observation`
  - `TraceGenerator`、`ObservationGenerator`
  - `Reporter`（接口）、`KafkaReporter`、`FileReporter`
  - `TraceIdGenerator`、`ObservationIdGenerator`
- [ ] 定义配置文件模板（application.yaml）
- [ ] 生成 README.md（使用说明）
- [ ] 生成 Demo 示例（主类运行生成示例 trace）
- [ ] 创建完整单元测试类（覆盖生成器、Reporter、Id 工具）
- [ ] 生成架构图（PlantUML 格式）
- [ ] 输出 build pipeline（Maven + GitHub Actions）

## current_status

刚立项，未创建代码结构；  
目标是通过 AI 帮助搭建基础项目骨架、目录、配置、Reporter 多态实现与完整测试体系。
