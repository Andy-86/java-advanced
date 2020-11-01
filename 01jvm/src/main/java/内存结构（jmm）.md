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

*XMind - Trial Version*