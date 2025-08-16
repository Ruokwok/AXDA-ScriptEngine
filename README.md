## AXDA - Script Engine
Nukkit-MOT平台上的JavaScript脚本插件加载器，基于GraalVM实现。
兼容 [LegacyScriptEngine](https://lse.levimc.org/zh/)(LSE)的API。

ASE可以在绝大部分JVM上运行(>=17)，在使用GraalVM时，需安装js模块，命令如下:

`gu install js`

### 安装脚本插件
- JS脚本插件存放在 `plugins/` 目录下，启动服务器插件将会自动加载。

### 命令
- `ase ls` 打印已加载的脚本插件列表
- `ase reload` 重新加载所有的脚本插件

### LSE兼容性
ASE API目标是兼容绝大部分LSE API，基于LSE开发的JavaScript插件可以直接在ASE上运行，查看LSE文档请移步至[https://lse.levimc.org/zh/](https://lse.levimc.org/zh/)

#### Level差异
众所周知，BDS原生不支持多世界特性(多存档)，只存在维度，所以LSE API中不存在类似Nukkit Level的概念。
而Nukkit支持多世界特性，ASE上一些关于世界的API接口(如传送、设置时间、设置天气)，相比LSE API，需要传入**Level**对象。
同时提供了兼容LSE的写法，在不传入**Level**对象时，所有操作作用于默认世界。

#### KVDatabase差异
ASE的KVDatabase接口虽然底层和LSE一样使用LevelDB实现，但是序列化/反序列化实现方式有所不同，所以在LSE上创建的数据库无法迁移到ASE上使用。

## 已实现的API
以下为ASE当前已实现的API接口列表，具体参数和返回值请参考LSE文档。
### Data API
#### JSON 格式配置文件
`var conf = new JsonConfigFile(path[,default])` 创建 / 打开一个 JSON 配置文件

`conf.init(name,default)` 初始化配置项（方便函数）

`conf.set(name,data)` 写入配置项

`conf.get(name[,default])` 读取配置项

`conf.delete(name)` 删除配置项
#### KVDatabase
`var kvdb = new KVDatabase(dir)` 创建一个KVDatabase对象

`kvdb.set(name,data)` 写入数据项

`kvdb.get(name)` 读取数据项

`kvdb.delete(name)` 删除数据项

`kvdb.listKey(name)` 获取所有数据项名字

`kvdb.close()` 关闭数据库

### Event API
`mc.listen(event,callback)` 注册监听器

#### 玩家相关事件
`"onPreJoin"` 玩家开始连接服务器

`"onJoin"` 玩家进入游戏（加载世界完成）

`"onLeft"` 玩家离开游戏

`"onJump"` 玩家跳跃

`"onRespawn"` 玩家重生

`"onPlayerDie"` 玩家死亡

`"onPlayerCmd"` 玩家执行命令

`"onChat"` 玩家发送聊天信息

`"onChangeDim"` 玩家切换维度

`"onSneak"` 玩家切换潜行状态

### Game API
#### 游戏元素对象 (数据类型)
`IntPos` 整数坐标

`FloatPos` 浮点坐标

`Player` 玩家对象

`Entity` 实体对象
#### 游戏实用工具 API

`Format` 格式化代码实用工具

#### 实体对象API
`en.name` 实体名称

`en.type` 实体标准类型名

`en.id` 实体的游戏内 id

`en.feetPos` 实体腿部所在坐标

`en.blockPos` 实体所在的方块坐标

`en.maxHealth` 实体最大生命值

`en.health` 玩家当前生命值

#### 玩家对象API
`var pl = mc.getPlayer(info)` 通过玩家信息手动生成玩家对象

`var pl = mc.getOnlinePlayers()` 获取所有在线玩家

`pl.name` 玩家名称

`pl.pos` 玩家坐标

`pl.blockPos` 玩家所在的方块坐标

`pl.realName` 玩家的真实名字

`pl.xuid` 玩家 XUID 字符串

`pl.uuid` 玩家 UUID 字符串

`pl.gameMode` 玩家的游戏模式（0 - 2, 6）

`pl.maxHealth` 玩家最大生命值

`pl.inWorld` 玩家是否在世界

`pl.speed` 玩家当前速度

`pl.langCode` 玩家设置的语言的标识符(形如 zh_CN)

`pl.isAdventure` 玩家是否是冒险模式

`pl.isSurvival` 玩家是否是生存模式

`pl.isSpectator` 玩家是否是旁观者模式

`pl.isCreative` 玩家是否是创造模式

`pl.isSleeping` 玩家是否正在睡觉

`pl.isSneaking` 玩家是否正在潜行

`pl.tell(msg)` `pl.sendText(msg)` 发送一个文本消息给玩家

`pl.isOp()` 判断玩家是否为OP

`pl.kick()` `pl.disconnect()` 断开玩家连接

`pl.setTitle(content[,type[,fadeInTime,stayTime,fadeOutTime]])` 设置玩家显示标题

`pl.sendToast(title,message)` 在屏幕上方显示消息(类似于成就完成)

`mc.broadcast(msg[,type])` 广播一个文本消息给所有玩家

`pl.runcmd(cmd)` 以某个玩家身份执行一条命令

`pl.talkAs(text)` 以某个玩家身份说话

`pl.distanceTo(pos)` `pl.distanceToSqr(pos)` 获取玩家到坐标的距离

`pl.talkTo(text,target)` 以某个玩家身份向某玩家说话

`pl.teleport(pos[,rot])` `pl.teleport(x,y,z,dimid[,rot])` 传送玩家至指定位置

`pl.kill()` 杀死玩家

`pl.setGameMode(mode)` 修改玩家游戏模式

`pl.getLevel()` 获取玩家经验等级

`pl.setLevel(count)` 设置玩家经验等级

`pl.resetLevel()` 重置玩家经验

`pl.getCurrentExperience()` 获取玩家当前经验值

`pl.setCurrentExperience(count)` 设置玩家当前经验值

`pl.addExperience(count)` 提高玩家经验值

`pl.reduceExperience(count)` 降低玩家经验值

### GUI API
`pl.sendModalForm(title,content,confirmButton,cancelButton,callback[,forUpdating])` 向玩家发送模式表单

`pl.sendSimpleForm(title,content,buttons,images,callback[,forUpdating])` 向玩家发送普通表单

`pl.sendCustomForm(json,callback[,forUpdating])` 向玩家发送自定义表单（JSON格式）

`pl.closeForm()` 关闭玩家正在打开的表单

#### 普通表单构建器 API
`var fm = mc.newSimpleForm()` 创建新的普通表单对象

`var fm = mc.newCustomForm()` 创建新的自定义表单对象

`fm.setTitle(title)` 设置表单的标题

`fm.setContent(content)` 设置表单的内容

`fm.addButton(text[,image])` 向表单内增加一行按钮

`fm.addHeader(text)` 向表单内增加标头

`fm.addLabel(text)` 向表单内增加一行文本

`fm.addDivider()` 向表单内增加分隔线

`fm.addInput(title[,placeholder,default,tooltip])` 向表单内增加一行输入框

`fm.addSwitch(title[,default,tooltip])` 向表单内增加一行开关

`fm.addDropdown(title,items[,default,tooltip])` 向表单内增加一行下拉菜单

`fm.addSlider(title,min,max[,step,default,tooltip])` 向表单内增加一行游标滑块

`fm.addStepSlider(title,items[,default,tooltip])` 向表单内增加一行步进滑块

`fm.setSubmitButton(text)` 设置表单的提交按钮文本

`pl.sendForm(fm,callback[,forUpdating])` 发送表单

### Script API
#### 通用日志API
`logger.setConsole(isOpen[,logLevel])` 设置日志是否输出到控制台

`logger.setFile(filepath[,logLevel])` 设置日志是否输出到文件

`logger.setPlayer(player[,logLevel])` 设置日志是否输出到某个玩家

`logger.log(data1,data2,...)` 输出普通文本

`logger.debug(data1,data2,...)` 输出调试信息

`logger.info(data1,data2,...)` 输出提示信息

`logger.warn(data1,data2,...)` 输出警告信息

`logger.error(data1,data2,...)` 输出错误信息

`logger.fatal(data1,data2,...)` 输出严重错误信息

`logger.setTitle(title)` 设置自定义日志消息标头

`logger.setLogLevel(level)` 统一修改日志输出等级
#### 脚本辅助 API
`log(data1,data2,...)` 输出信息到控制台

`setTimeout(func,msec)` 推迟一段时间执行函数

`setInterval(func,msec)` 设置周期执行函数

`clearInterval(taskid)` 取消延时 / 周期执行项
#### 插件加载相关 API
`ll.language` Nukkit使用的语言。(例如zh_Hans、en和ru_RU)

`ll.major` 主版本号（如 2.1.0 里的 2）

`ll.minor` 次版本号（如 2.1.0 里的 1）

`ll.revision` 修订版本号（如 2.1.0 里的 0）

`ll.status` 版本状态 (0为Dev, 1为Beta, 2为Release)

`ll.scriptEngineVersion` 脚本引擎的版本

`ll.isWine` 是否处于Wine环境下

`ll.isDebugMode` 是否处于debug模式

`ll.isBeta` 当前版本是否为测试版

`ll.isDev` 当前版本是否为开发版

`ll.isRelease` 当前版本是否为发布版本

`ll.pluginsRoot` Nukkit插件的根目录

`ll.versionString()` 获取 Nukkit 版本字符串
### System API
#### 简单文件读写 API
`File.readFrom(path)` 读入文件的所有内容

`File.writeTo(path,text)` 向指定文件写入内容

`File.writeLine(path,text)` 向指定文件追加一行
#### 目录与文件 API
`File.createDir(dir)` `File.mkdir(dir)` 创建文件夹

`File.delete(path)` 删除文件 / 文件夹

`File.exists(path)` 判断文件 / 文件夹是否存在

`File.copy(from,to)` 复制文件 / 文件夹到指定位置

`File.move(from,to)` 移动文件 / 文件夹到指定位置

`File.rename(from,to)` 重命名指定文件 / 文件夹

`File.getFileSize(path)` 获取指定文件的大小

`File.checkIsDir(path)` 判断指定路径是否是文件夹

`File.getFilesList(dir)` 列出指定文件夹下的所有文件 / 文件夹
#### 网络接口 API
##### WebSocket 客户端对象 API
`var wsc = new WSClient()` 创建一个新的WebSocket 客户端对象

`wsc.status` 当前的连接状态

`wsc.connect(target)` 创建连接

`wsc.connectAsync(target,callback)` 异步创建连接

`wsc.send(msg)` 发送文本 / 二进制消息

`wsc.close()` 关闭连接

`wsc.shutdown()` 关闭连接

`wsc.listen(event,callback)` 监听WebSocket事件

**监听事件列表**

`"onTextReceived"` 收到文本消息

`"onBinaryReceived"` 收到二进制消息

`"onError"` 发生错误

`"onLostConnection"` 连接丢失
#### 获取系统信息 API
`system.getTimeStr()` 获取当前时间字符串

`system.getTimeObj()` 获取当前的时间对象

`system.randomGuid()` 随机生成一个 GUID 字符串