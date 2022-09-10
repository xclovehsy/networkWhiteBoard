# WhiteBoard
网络白板程序的java实现

## 1. 简介

### 1.1. 项目需求

网络白板利用面相对象的思想设计适合可扩展的图形类集，利用java gui的mvc模式设计用户的绘图流程，利用java的套接字编程实现多客户端的数据共享方法，利用多线程机制实现绘图和数据传输的并发控制机制

### 1.2. 实现功能

1. 程序能够在窗体上根据用户的选择绘制不同形状（3个以上）的图形；
2. 程序能够修改图形的属性（颜色和大小）和位置（利用鼠标移动选定的图形）；
3. 网络客户端的程序能够协同绘图（一个用户绘图其他用户均可见绘图效果）；
4. 程序能够以文件的形式保存绘图结果，下次启动程序后能够读取绘图结果文件再现绘图效果。

**开发语言：**
Java

**开发平台：**
Intellij IDEA 2021.2.2



## 2. 项目设计

### 2.1. MVC设计流程：

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209061101956.gif)

1. GUIServer作为服务器接受客户端发送的图形数据，并将图形数据写入Data.txt文件中保存，同时将图形数据发送到各个客户端。
2. 用户操作OpenGLApp画板。每画一个图形，程序会将数据发送到服务器。同时接受服务器传输的图形数据进行绘图。
3. Data.txt文件用于保存图形数据，每当服务器开启时将会读取文件中上次保存的图形数据，并发送到各个客户端进行绘图。

### 2.2. 程序架构设计

#### 2.2.1. 整体结构框架

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209061101514.gif)

该程序代码共有三个部分

1. app为网络白板客户的主程序
2. graph为图形包，里面包含了各种图形的数据，例如圆形、立方体、长方形等等。
3. server负责服务器数据的接受与发送，以及数据的保存
4. Data.txt用于保存图形数据

---



#### 2.2.2. app结构

OpenGLApp为网络白板客户端的主程序，负责GUI的显示，图形的绘制、发送数据、接受服务器发送的数据等，同时实现了1、根据用户的选择绘制不同形状（3个以上）的图形；2、修改图形的属性（颜色和大小）和位置（利用鼠标移动选定的图形）等功能。

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209061102231.gif)

 

---



#### 2.2.3. graph结构

1. Shape类是图形类的抽象父类, 它包含一个抽象方法draw(),在他的派生类中都实现了draw（）方法、各自的属性和属性的修改方法；
2. Graphic是用来存储当前已有的对象和绘制已有的对象；
3. Cube、Circle、Rectangle等均是Shape的子类。

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209061103998.gif)

**Graph包类图如下（可见附件三）**

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209061104406.jpg)

---

#### 2.2.4. server结构

1.	GUIServer为服务器端，接受客户端发送的图形数据，并将图形数据写入Data.txt文件中保存。
2.	SeverThread多线程操作，可以并发的接受多个客户端的数据，并传送数据。

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209061105343.gif)

### 2.3. UML类图

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209061105680.jpg)

 

## 3. 项目展示

### 3.1. 主界面

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209061106996.gif)

 

### 3.2. 功能测试

#### 3.2.1 绘制图形

**程序能够在窗体上根据用户的选择绘制不同形状（3个以上）的图形；**

 

该程序可以根据用户需要绘制不同的图形，通过点击面板上不同的图形按钮设置当前绘制的图形种类。例如圆形、椭圆、立方体、长方形、三角形等

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209061106005.gif)

 

 #### 3.2.2. 修改图形



**程序能够修改图形的属性（颜色和大小）和位置（利用鼠标移动选定的图形）；**

 

可以选择不同的线条颜色以及填充颜色

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209061106011.gif)



#### 3.2.3. 网络协同

**网络客户端的程序能够协同绘图（一个用户绘图其他用户均可见绘图效果）；**

打开sever.GUISever服务器程序，等待其他画板的连接

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209061107060.gif)

 

同时将网络白板客户端程序运行4次服务器连接上程序

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209061107064.gif)

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209061107082.gif)

- 在客户端1绘制图形将会传输其他客户端白板中。
- 在一个客户端中对图形进行修改，其他客户端的图形也会修改。即网络客户端的程序能够协同绘图（一个用户绘图其他用户均可见绘图效果）；

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209061107093.gif)

---

**更多功能请查看说明文档**
