# JustLook

一个基于MVP+Retrofit+Glide+MaterialDesign的知乎日报客户端，使用的知乎API来源于[知乎日报API分析](https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90)
## 效果图
![](http://img.blog.csdn.net/20170213193638021?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZ3Vhc3o=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 1.整体风格以及侧滑侧滑菜单
整体采用MaterialDesign风格设计，ToolBar+DrawerLayout+NavigationView，轮播图采用ConvenientBanner 
![](http://img.blog.csdn.net/20170219215443786?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZ3Vhc3o=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 2.下拉刷新以及加载更多功能
下拉刷新采用SwipeRefreshLayout，加载更多采用第三方BaseRecyclerViewAdapterHelper 
![](http://img.blog.csdn.net/20170219223008067?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZ3Vhc3o=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 3.条目详情页面
采用可折叠标题栏，CoordinatorLayout+AppBarLayout+CollapsingToolbarLayout+ToolBar，点击FloatingActionButton可收藏或取消收藏，并且设置FloatingActionButton上拉隐藏，下滑显示 
![](http://img.blog.csdn.net/20170219223617429?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZ3Vhc3o=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 4.收藏页面
采用数据库保存收藏条目信息并展示 
![](http://img.blog.csdn.net/20170219223704773?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZ3Vhc3o=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 5.主题切换功能
由于采用别的主题切换方案存在bug，暂时只能实现主题切换需要重启才能生效，主题选择界面自定义了一个流式布局 
![](http://img.blog.csdn.net/20170219224111794?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZ3Vhc3o=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 6.设置功能
设置选项目前有清除缓存已经切换夜间模式功能，夜间模式只是实现了另外一套主题，并不是真正意义上的夜间模式，从gif图也可以看出来 
![](http://img.blog.csdn.net/20170219224215902?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZ3Vhc3o=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 使用的第三方开源库
> 数据来源：[知乎API](https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90)
> 图片加载框架：[Glide](https://github.com/bumptech/glide)
> 网络请求框架：[Retrofit](https://github.com/square/retrofit)
> view注解框架：[ButterKnife](https://github.com/JakeWharton/butterknife)
> 基类适配器：[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
> 轮播图：[ConvenientBanner](https://github.com/saiwu-bigkoo/Android-ConvenientBanner)
> 事件总线：[EventBus](https://github.com/greenrobot/EventBus)


