
<p align="center">
    <img height="165" src="ase_logo.png">
</p>

[![stars](https://img.shields.io/github/stars/Ruokwok/AXDA-ScriptEngine)](https://github.com/Ruokwok/AXDA-ScriptEngine)
[![license](https://img.shields.io/github/license/Ruokwok/AXDA-ScriptEngine)](https://github.com/Ruokwok/AXDA-ScriptEngine/blob/master/LICENSE)
[![version](https://img.shields.io/badge/version-1.0.0-red)](https://github.com/Ruokwok/AXDA-ScriptEngine)

## AXDA - Script Engine
Nukkit-MOT平台上的JavaScript脚本插件加载器，基于GraalVM实现。
兼容 [LegacyScriptEngine](https://lse.levimc.org/zh/)(LSE)的API。完全支持ES2022语法标准和特性。

ASE可以在绝大部分JVM上运行(>=17)，在使用GraalVM时，可能需安装js模块，命令如下:

`gu install js`

### 下载
[MOTCI Jenkins](https://motci.cn/job/AXDA-ScriptEngine/)
### 安装脚本插件

**简单安装:**
-  将JS脚本插件存放在 `plugins/` 目录下，启动服务器插件将会自动加载。

**ZIP压缩包安装:**
-  将zip插件包存放在 `plugins/` 目录下，启动服务器将自动解压清单和插件等文件。

### 命令
- `ase ls` 打印已加载的脚本插件列表
- `ase reload` 重新加载所有的脚本插件
- `ase unload <plugin>` 卸载指定插件

### LSE兼容性
ASE API目标是兼容绝大部分LSE API，基于LSE开发的JavaScript插件可以直接在ASE上运行，查看LSE文档请移步至[https://lse.levimc.org/zh/](https://lse.levimc.org/zh/)

#### Level差异
在LSE中，对于世界的操作只需传入int类型的维度ID，而在ASE中的相应API函数，除了维度ID外，还可传入String类型的世界名称。

#### KVDatabase差异
ASE的KVDatabase接口虽然底层和LSE一样使用LevelDB实现，但是序列化/反序列化实现方式有所不同，所以在LSE上创建的数据库无法迁移到ASE上使用。

### 致谢
AXDA-ScriptEngine的实现离不开以下项目:
- [Nukkit-MOT](https://github.com/MemoriesOfTime/Nukkit-MOT)
- [PNX LLSE-Lib](https://github.com/PowerNukkitX-Bundle/LLSE-Lib)
- [LegacyScriptEngine](https://github.com/LiteLDev/LegacyScriptEngine)