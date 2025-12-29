# Custom Langfuse Traces

一个 Java 模块，用于快速生成 Langfuse 格式的 trace 功能接口，包含 Langfuse 各种类型的 observation、trace 对象，并支持将生成结果上报到 Kafka 或打印到本地文件。

## 项目背景

用户需要生成 Langfuse 格式的 trace，上报到 Langfuse 平台，以追踪大模型 Agent 执行链路。用户要求每条 trace，要支持指定 trace 所属的 project id，但现有的 trace 框架在初始化时，就必须要绑定 trace 对象所属的项目，这个 trace 对象比较重，无法满足用户要求的使用 trace 创建 span 时，灵活指定这个 span 所属的 project，因此需要自研一个支持生成 Langfuse trace 的模块 API，支持用户手动生成各种类型的 Langfuse trace。

Langfuse 的 trace 包含两个概念，trace 和 observation，observation 即是 OpenTelemetry 里面的 span，一个 trace_id 会产生一个 trace。

## 功能特性

- **灵活的项目分配**：支持在创建 trace 和 observation 时灵活指定 project id
- **多种上报方式**：支持上报到 Kafka 和打印到本地文件两种方式
- **完整的数据模型**：支持 Langfuse 所有类型的 observation (SPAN, GENERATION, EVENT)
- **Trace 上下文传递**：提供 TraceContext 对象在方法之间传递，避免使用 ThreadLocal
- **通用属性配置**：支持初始化时设置通用 trace 属性

## 项目结构

```
custom-langfuse-traces/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/antigravity/langfuse/traces/
│   │   │       ├── config/
│   │   │       │   └── TraceConfig.java
│   │   │       ├── context/
│   │   │       │   └── TraceContext.java
│   │   │       ├── dto/
│   │   │       │   ├── Trace.java
│   │   │       │   └── Observation.java
│   │   │       ├── service/
│   │   │       │   ├── TraceService.java
│   │   │       │   └── TraceServiceImpl.java
│   │   │       ├── kafka/
│   │   │       │   ├── KafkaTraceReporter.java
│   │   │       │   └── KafkaTraceReporterImpl.java
│   │   │       └── file/
│   │   │           ├── FileTraceReporter.java
│   │   │           └── FileTraceReporterImpl.java
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/antigravity/langfuse/traces/
└── README.md
```

## 使用示例

### 1. 初始化 TraceService

```java
import com.antigravity.langfuse.traces.config.TraceConfig;
import com.antigravity.langfuse.traces.service.TraceService;
import com.antigravity.langfuse.traces.service.TraceServiceImpl;

// 创建配置
TraceConfig config = new TraceConfig();
config.setOutputType(TraceConfig.OutputType.KAFKA); // 或 FILE, BOTH

// Kafka 配置
TraceConfig.KafkaConfig kafkaConfig = new TraceConfig.KafkaConfig();
kafkaConfig.setBootstrapServers("localhost:9092");
kafkaConfig.setTopicName("langfuse-traces");
config.setKafkaConfig(kafkaConfig);

// 文件输出配置
TraceConfig.FileOutputConfig fileConfig = new TraceConfig.FileOutputConfig();
fileConfig.setFilePath("./langfuse-traces.json");
config.setFileOutputConfig(fileConfig);

// 初始化服务
TraceService traceService = new TraceServiceImpl();
traceService.initialize(config);
```

### 2. 创建 Trace

```java
// 创建 trace
String traceId = "my-trace-id";
String projectId = "my-project-id";
Trace trace = traceService.createTrace(traceId, projectId, "My Trace Name");

// 上报 trace
traceService.reportTrace(trace);
```

### 3. 创建 Observation

```java
import com.antigravity.langfuse.traces.context.TraceContext;

// 创建 trace 上下文
TraceContext context = TraceContext.builder()
    .traceId(traceId)
    .projectId(projectId)
    .build();

// 创建 observation
String observationId = "my-observation-id";
Observation observation = traceService.createObservation(context, observationId, "SPAN", "My Observation");

// 上报 observation
traceService.reportObservation(observation);
```

### 4. 使用 Trace 上下文传递

```java
// 在方法间传递上下文
public void methodWithTrace(TraceContext context) {
    Observation observation = traceService.createObservation(context, 
        UUID.randomUUID().toString(), "SPAN", "Sub-operation");
    traceService.reportObservation(observation);
}
```

## 数据模型

### Trace 字段

根据 ClickHouse 表结构定义：

- `id`: String
- `timestamp`: DateTime64(3)
- `name`: String
- `user_id`: Nullable(String)
- `metadata`: Map(LowCardinality(String), String)
- `release`: Nullable(String)
- `version`: Nullable(String)
- `project_id`: String
- `environment`: LowCardinality(String) DEFAULT 'default'
- `public`: Bool
- `bookmarked`: Bool
- `tags`: Array(String)
- `input`: Nullable(String) CODEC(ZSTD(3))
- `output`: Nullable(String) CODEC(ZSTD(3))
- `session_id`: Nullable(String)
- `created_at`: DateTime64(3) DEFAULT now()
- `updated_at`: DateTime64(3) DEFAULT now()
- `event_ts`: DateTime64(3)
- `is_deleted`: UInt8

### Observation 字段

根据 ClickHouse 表结构定义：

- `id`: String
- `trace_id`: String
- `project_id`: String
- `environment`: LowCardinality(String) DEFAULT 'default'
- `type`: LowCardinality(String)
- `parent_observation_id`: Nullable(String)
- `start_time`: DateTime64(3)
- `end_time`: Nullable(DateTime64(3))
- `name`: String
- `metadata`: Map(LowCardinality(String), String)
- `level`: LowCardinality(String)
- `status_message`: Nullable(String)
- `version`: Nullable(String)
- `input`: Nullable(String) CODEC(ZSTD(3))
- `output`: Nullable(String) CODEC(ZSTD(3))
- `provided_model_name`: Nullable(String)
- `internal_model_id`: Nullable(String)
- `model_parameters`: Nullable(String)
- `provided_usage_details`: Map(LowCardinality(String), UInt64)
- `usage_details`: Map(LowCardinality(String), UInt64)
- `provided_cost_details`: Map(LowCardinality(String), Decimal(18, 12))
- `cost_details`: Map(LowCardinality(String), Decimal(18, 12))
- `total_cost`: Nullable(Decimal(18, 12))
- `completion_start_time`: Nullable(DateTime64(3))
- `prompt_id`: Nullable(String)
- `prompt_name`: Nullable(String)
- `prompt_version`: Nullable(UInt16)
- `created_at`: DateTime64(3) DEFAULT now()
- `updated_at`: DateTime64(3) DEFAULT now()
- `event_ts`: DateTime64(3)
- `is_deleted`: UInt8

## 依赖

- Java 21
- Apache Kafka Client 3.7.0
- Jackson 2.15.2
- SLF4J 2.0.9

## 构建

```bash
mvn clean install
```

## 测试

```bash
mvn test
```

## 许可证

[在此处添加许可证信息]