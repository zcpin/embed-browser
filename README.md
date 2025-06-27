# 📘 Embed Browser Plugin for JetBrains IDEs

一个用于在 JetBrains 系列 IDE 中嵌入浏览器查看开发文档的插件，支持收藏夹、地址栏、移动端显示模拟等功能，避免频繁切换到外部浏览器，大幅提升开发效率。

---

## ✨ 功能特点

- 🌐 内嵌 Chromium（JCEF）浏览器
- ⭐ 收藏夹支持添加、删除、双击跳转
- 📂 收藏夹可折叠/展开，节省空间
- 🧭 地址栏显示并同步当前网页地址，支持手动跳转
- 📱 支持切换移动端 User-Agent 模式查看页面
- 🖼 操作按钮集成在工具窗口标题栏，支持自定义图标（SVG）
- 🧩 完整支持 JetBrains Plugin SDK，兼容 IntelliJ IDEA、WebStorm、PyCharm 等

---

## 📦 安装方式

### 方法一：本地运行测试

```bash
git clone https://github.com/zcpin/embed-browser.git
cd embed-browser
./gradlew runIde
```

### 方法二：手动安装
1. 执行打包命令：
`./gradlew buildPlugin`
2. 打开 JetBrains IDE，依次点击
`Settings / Preferences → Plugins → ⚙ → Install plugin from disk...`
3. 选择 build/distributions/*.zip 插件包


