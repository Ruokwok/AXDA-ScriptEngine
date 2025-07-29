## AXDA - Script Engine
Nukkit平台上的JavaScript脚本插件加载器，基于GraalVM实现。
兼容 [LegacyScriptEngine](https://lse.levimc.org/zh/)(LSE)的API。

### 安装脚本插件
- JS脚本插件存放在 `plugins/` 目录下，启动服务器插件将会自动加载。

### 命令
- `ase ls` 打印已加载的脚本插件列表
- `ase reload` 重新加载所有的脚本插件

### LSE兼容性
ASE API目标将兼容绝大部分LSE API，基于LSE开发的JavaScript插件可以直接在ASE上运行，查看LSE文档请移步至[https://lse.levimc.org/zh/](https://lse.levimc.org/zh/)

#### Level差异
众所周知，BDS原生不支持多世界特性(多存档)，只存在维度，所以LSE API中不存在类似Nukkit Level的概念。
而Nukkit支持多世界特性，ASE上一些关于世界的API接口(如传送、设置时间、设置天气)，相比LSE API，需要传入**Level**对象。
同时提供了兼容LSE的写法，在不传入**Level**对象时，所有操作作用于默认世界。

#### KVDatabase差异
ASE的KVDatabase接口虽然底层和LSE一样使用LevelDB实现，但是序列化/反序列化实现方式有所不同，所以在LSE上创建的数据库无法迁移到ASE上使用。