#字节码总结
# 字节码

## 什么是字节码

### Java bytecode 由单字节（byte）的指令组成，理论上最多支持 256 个操作码（opcode）。
实际上 Java 只使用了200左右的操作码， 还有一些操作码则保留给调试操作

## 为什么用字节码

### 同一份代码，先编译成一份通用的二进制文件，然 后分发到不同平台，由虚拟机运行时来加载和执行，这样就会综合另外两种跨平 台语言的优势，方便快捷地运行于各种平台。一次编写到处运行

# 类加载

## 类生命周期

### 加载

- 找 Class 文件：获取二进制classfile格式的字节流

### 验证

- 验证格式、依赖：校验范围主要包括文件格式、编译版本号、常量池符号和类型

### 准备

- 静态字段、方法表：创建静态变量并赋初值、如果静态变量是final修饰的话会直接赋值、分配方法表,即在方法区中分配这些变量所使用的内存空间

### 解析

- 解析常量池中的符号引用转为直接引用，包括类或接口的解析、字段解析、类方法解析、接 口方法解析

### 初始化

- 执行构造函数、执行静态代码块、静态变量赋初值

### 使用

### 卸载

## 类加载的时机

### 虚拟机启动的时候初始化指定的主类，启动执行main方法的类

### new一个类的时候，该类会进行初始化

### 调用类的静态方法，该类会被加载

### 调用类的静态成员变量，该类会被加载

### 子类初始化的时候触发父类也被初始化

### 定义了default方法的接口，直接或间接实现了该接口的类初始化的时候会触发该类初始化

### 使用反射创建实例的时候，会进行初始化（和new一样）

### 当初次调用 MethodHandle 实例时，初始化该 MethodHandle 指向的方法所在的类

## 类不会初始化可能会加载

### 通过子类引用父类的静态字段，父类不会初始化

### 定义对象数组，该类不会初始化

### 常量在编译期间会存入调用类的常量池中，本质上并没有直接引用定义常量的类，不会触发定义常量所在的类的初始化

static class A {
    private final static int a = 1;
}

static class B {
    private final static int b = A.a;
}
编译后b变量直接是字面量1，和A没有关联

### 通过类名获取Class对象，不会触发类的初始化

### 通过 Class.forName 加载指定类时，如果指定参数 initialize 为 false 时，也不会触发类初始化，其实这个参数是告诉虚拟机，是否要对类进行初始化

### 通过 ClassLoader 默认的 loadClass 方法，也不会触发初始化动作（加载了，但是不初始化）

## 类加载器

### 启动类加载器BootstrapClassLoader

- 加载java核心类库，如jre/lib目录下的rt.jar。它是由C++编写

### 扩展类加载器ExtClassLoader

- 加载扩展目录下的类（lib/ext或者java.ext.dirs属性指定的目录）

### 应用类加载器AppClassLoader

- 加载classpath目录、java.class.path系统属性指定目录下的类

### 自定义类加载器

## 类加载机制

### 双亲委派

- 当一个自定义类加载器需要加载一个类，比如java.lang.String，它很 懒，不会一上来就直接试图加载它，而是先委托自己的父加载器去加载，父加载 器如果发现自己还有父加载器，会一直往前找，这样只要上级加载器，比如启动 类加载器已经加载了某个类比如java.lang.String，所有的子加载器都不需要自己 加载了。如果几个类加载器都没有加载到指定名称的类，那么会抛出 ClassNotFountException异常

### 负责依赖

- 如果一个加载器在加载某个类的时候，发现这个类依赖于另外几个类 或接口，也会去尝试加载这些依赖项

### 缓存加载

- 为了提升加载效率，消除重复加载，一旦某个类被一个类加载器加 载，那么它会缓存这个加载结果，不会重复加载

#内存结构（jmm）总结
# 内存结构（jmm）

## 栈Stack

### 线程栈

- 栈帧Frame

	- 本地变量表
	- 操作数栈
	- 返回值
	- Class、Method指针，指向非堆中的类、方法

## 堆Heap

### 年轻代Young generation

- 伊甸区
- 存活区

### 老年代Old generation

## 非堆Non-Heap

### 元数据区Metaspace

- 方法区
- 常量池

### CCS

- 存放 class 信息的，和 Metaspace 有交叉

### Code Cache

- 存放 JIT 编译器编译后的本地机器
代码

#jvm参数总结
# jvm参数

## 系统属性参数

### -Dfile.encoding=UTF-8

### -Duser.timezone=GMT+08

## 运行模式参数

### -server

- 设置 JVM 使用 server 模式，特点是启动速度比较慢，但运行时性能和内存管理效率
很高，适用于生产环境。在具有 64 位能力的 JDK 环境下将默认启用该模式，而忽略 -client 参数

### -client

- JDK1.7 之前在32位的 x86 机器上的默认值是 -client 选项。设置 JVM 使用 client 模
式，特点是启动速度比较快，但运行时性能和内存管理效率不高，通常用于客户端应用程序或
者 PC 应用开发和调试。此外，我们知道 JVM 加载字节码后，可以解释执行，也可以编译成本
地代码再执行，所以可以配置 JVM 对字节码的处理模式

### -Xint

- 在解释模式（interpreted mode）下运行，-Xint 标记会强制 JVM 解释执行所有的字节
码，这当然会降低运行速度，通常低10倍或更多

### -Xcomp

- -Xcomp 参数与-Xint 正好相反，JVM 在第一次使用时会把所有的字节码编译成本地
代码，从而带来最大程度的优化。【注意预热】

### -Xmixed

- -Xmixed 是混合模式，将解释模式和编译模式进行混合使用，有 JVM 自己决定，这
是 JVM 的默认模式，也是推荐模式。

## 堆内存参数

### -Xmx

- 指定最大堆内存。 如 -Xmx4g. 这只是限制了 Heap 部分的最大值为4g。
这个内存不包括栈内存，也不包括堆外使用的内存

### -Xms

- 指定堆内存空间的初始大小。 如 -Xms4g。 而且指定的内存大小，并
不是操作系统实际分配的初始值，而是GC先规划好，用到才分配。 专用服务
器上需要保持 –Xms 和 –Xmx 一致，否则应用刚启动可能就有好几个 FullGC。
当两者配置不一致时，堆内存扩容可能会导致性能抖动

### -Xmn

- 等价于 -XX:NewSize，使用 G1 垃圾收集器 不应该 设置该选项，在其
他的某些业务场景下可以设置。官方建议设置为 -Xmx 的 1/2 ~ 1/4.

### -XX:MaxPermSize=size

- JDK1.7 之前使用的。Java8 默认允许的
Meta空间无限大，此参数无效。

### -XX:MaxMetaspaceSize=size

- Java8 默认不限制 Meta 空间, 一般不允许设
置该选项。

### -XX:MaxDirectMemorySize=size

- 系统可以使用的最大堆外内存，这个参
数跟 -Dsun.nio.MaxDirectMemorySize 效果相同

### -Xss

- 设置每个线程栈的字节数。 例如 -Xss1m 指定线程栈为 1MB，与-
XX:ThreadStackSize=1m 等价

## GC设置参数

### -XX:+UseG1GC

- 使用 G1 垃圾回收器（JDK9+默认）

### -XX:+UseConcMarkSweepGC

- 使用 CMS 垃圾回收器

### -XX:+UseSerialGC

- 使用串行垃圾回收器

### -XX:+UseParallelGC

- 使用并行垃圾回收器（JDK8默认）

### -XX:+UnlockExperimentalVMOptions -XX:+UseZGC

- 使用ZGC垃圾回收器（JDK11+）

### -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC

- 使用SGC垃圾回收器（JDK12+）

## 分析诊断参数

### -XX:+-HeapDumpOnOutOfMemoryError

- 当 OutOfMemoryError 产生，即内存溢出(堆内存或持久代)时，
自动 Dump 堆内存

### -XX:HeapDumpPath

- 与 HeapDumpOnOutOfMemoryError 搭配使用, 指定内存溢出时 Dump 文件的目
录

### -XX:OnError

- 发生致命错误时（fatal error）执行的脚本

### -XX:OnOutOfMemoryError

- 抛出 OutOfMemoryError 错误时执行的脚本

### -XX:ErrorFile=filename

- 致命错误的日志文件名,绝对路径或者相对路径

### -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1506

- 远程调试

## JavaAgent参数

### -agentlib:libname[=options]

- 启用 native 方式的 agent, 参考 LD_LIBRARY_PATH 路径。

### -agentpath:pathname[=options]

- 启用 native 方式的 agent。

### -javaagent:jarpath[=options]

- 启用外部的 agent 库, 比如 pinpoint.jar 等等

### -Xnoagent

- 禁用所有 agent

*XMind - Trial Version*

#诊断工具总结

#gc总结
