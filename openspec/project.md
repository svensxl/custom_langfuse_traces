# Project: custom-langfuse-traces

## 1. 项目目标
说明这个项目想解决什么问题，或者想实现什么结果。
> 本项目为一个java模块，提供快速生成langfuse格式的trace功能接口，包含langfuse的各种类型的observation, trace对象，并支持将生成结果上报到kafka，打印到本地文件两种方式
> 此模块初始化时支持传递一些通用trace属性，一些属性在每个observation中都会出现；支持设置

## 2. 项目背景
用户需要生成langfuse格式的trace，上报到langfuse平台，以追踪大模型Agent执行链路。用户要求每条trace，要支持指定trace所属的project id，但现有的trace框架在初始化时，就必须要绑定trace对象所属的项目，这个trace对象比较重，无法满足用户要求的使用trace创建span时，灵活指定这个span所属的project，因此需要自研一个支持生成langfuse trace的模块api，支持用户手动生成各种类型的langfuse trace
langfuse的trace包含两个概念，trace和observation，observation即是opentelemetry里面的span，一个trace_id会产生一个trace。
详细的资料你可以参考文档：
[Observation Types](https://langfuse.com/docs/observability/features/observation-types)
[Integrations with Opentelemetry](https://langfuse.com/integrations/native/opentelemetry)

## 3. 核心思路 / 初步设想
> 开发语言java 21, 构建工具maven
> 依赖库kafka client，支持上报到kafka 2.0集群，
> 模块要有初始化功能，初始化公共属性，设置上报方式，上报端配置
> 快速创建observation, trace, 一系列属性支持人工设置，包含trace id，project id
> 生成的trace 包含如下clickhouse表字段
    `id` String,

    `timestamp` DateTime64(3),

    `name` String,

    `user_id` Nullable(String),

    `metadata` Map(LowCardinality(String),
 String),

    `release` Nullable(String),

    `version` Nullable(String),

    `project_id` String,

    `environment` LowCardinality(String) DEFAULT 'default',

    `public` Bool,

    `bookmarked` Bool,

    `tags` Array(String),

    `input` Nullable(String) CODEC(ZSTD(3)),

    `output` Nullable(String) CODEC(ZSTD(3)),

    `session_id` Nullable(String),

    `created_at` DateTime64(3) DEFAULT now(),

    `updated_at` DateTime64(3) DEFAULT now(),

    `event_ts` DateTime64(3),

    `is_deleted` UInt8,

> 生成的observation包含如下clickhouse表字段
    `id` String,

    `trace_id` String,

    `project_id` String,

    `environment` LowCardinality(String) DEFAULT 'default',

    `type` LowCardinality(String),

    `parent_observation_id` Nullable(String),

    `start_time` DateTime64(3),

    `end_time` Nullable(DateTime64(3)),

    `name` String,

    `metadata` Map(LowCardinality(String),
 String),

    `level` LowCardinality(String),

    `status_message` Nullable(String),

    `version` Nullable(String),

    `input` Nullable(String) CODEC(ZSTD(3)),

    `output` Nullable(String) CODEC(ZSTD(3)),

    `provided_model_name` Nullable(String),

    `internal_model_id` Nullable(String),

    `model_parameters` Nullable(String),

    `provided_usage_details` Map(LowCardinality(String),
 UInt64),

    `usage_details` Map(LowCardinality(String),
 UInt64),

    `provided_cost_details` Map(LowCardinality(String),
 Decimal(18,
 12)),

    `cost_details` Map(LowCardinality(String),
 Decimal(18,
 12)),

    `total_cost` Nullable(Decimal(18,
 12)),

    `completion_start_time` Nullable(DateTime64(3)),

    `prompt_id` Nullable(String),

    `prompt_name` Nullable(String),

    `prompt_version` Nullable(UInt16),

    `created_at` DateTime64(3) DEFAULT now(),

    `updated_at` DateTime64(3) DEFAULT now(),

    `event_ts` DateTime64(3),

    `is_deleted` UInt8,
> 上报trace和observation两类数据，以json格式表示，字段参考上面定义
> 可考虑提供trace上下文对象，以在方法之间传递，无需要使用thread local这类技术

## 4. 预期成果
列出项目完成后应产生什么结果（文件、Demo、规范、网站等）。
> 库api
> 测试代码
> demo示例
> 项目技术文档，架构图等
> 用户使用文档


## 5. 当前状态
> 刚立项；还没创建代码结构；需要 AI 先帮我搭脚手架。