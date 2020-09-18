# MvvmLibTest（未完善，持续更新...）
一个基于MVVM使用AndroidJetpack封装的快速开发框架，包含Retrofit，Coroutine，DataBinding，ViewModel，LiveData，Room，Paging3
以及图片加载，版本更新，图片选择，常见的UI样式功能。

模仿朴朴APP样式，接口部分个人开发，只提供测试使用。
[API代码](https://github.com/yangbangwei/api)

## 效果截图 
<img src="images/snapshot1.png" width="30%"/><img src="images/snapshot2.png" width="30%"/><img src="images/snapshot3.png" width="30%"/>
<img src="images/snapshot4.png" width="30%"/><img src="images/snapshot5.png" width="30%"/>

## 第三方库
1. [`Okhttp` Okhttp网络访问框架](https://github.com/square/okhttp)
2. [`AgentWeb` 轻量级WebView框架](https://github.com/Justson/AgentWeb)
3. [`Retrofit` RESTful 的 HTTP 网络请求框架的封装](https://github.com/square/retrofit)
4. [`vlayout` RecyclerView的LayoutManager扩展](https://github.com/chrisbanes/PhotoView)
5. [`SmartRefreshLayout` Android智能下拉刷新框架](https://github.com/scwang90/SmartRefreshLayout)
6. [`BaseRecyclerViewAdapterHelper` 一个强大并且灵活的RecyclerViewAdapter](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
7. [`glide` Android图片加载框架](https://github.com/bumptech/glide)
8. [`AndroidUtilCode` AndroidUtilCode是一个强大易用的安卓工具类库](https://github.com/Blankj/AndroidUtilCode)
9. [`immersionbar` Android 4.4以上沉浸式实现](https://github.com/gyf-dev/ImmersionBar)
10. [`autosize` 屏幕适配](https://github.com/jackmoore/autosize)
11. [`banner` Banner广告控件](https://github.com/youth5201314/banner)
12. [`Leakcanary` 内存检测功能](https://github.com/square/leakcanary)
13. [`FlycoTabLayout` tabLayout页签](https://github.com/H07000223/FlycoTabLayout)
14. [`PermissionX` 动态权限管理](https://github.com/guolindev/PermissionX)
15. [`photoView` 图片预览](https://github.com/chrisbanes/PhotoView)

## 后续完善部分
- [x] 重新调整，优化流程，精简代码
- [x] 重新调整快速开发模板
- [x] glide 工具类基本用法使用
- [x] banner 基本功能使用
- [x] 首页tab功能实现
- [x] 发现页面，FlyCoTabLayout+ViewPager2效果
- [x] 动态权限使用PermissionX
- [x] autoSize屏幕适配
- [x] 无网络，异常问题
- [x] 重构网络通用处理部分
- [x] photoView控件
- [x] gsyVideoPlayer基本使用
- [x] 封装BaseDialogFragment弹窗，以及CommonDialog提醒。
- [ ] BUG收集
- [ ] 工具类整理
- [ ] 首页仿美团饿了么，多列表效果
- [ ] 商品页面仿购物类型APP，分类样式
- [ ] 版本更新控件
- [ ] 图片裁剪上传控件
- [ ] Paging3使用
- [ ] 相机功能
- [ ] 重新整理第三方框架
- [ ] 单元测试
- [ ] 功能完善后，将lib提交jCenter,jitPack

## 常用开发平台封装
- [ ] ShareSdk
- [ ] 个推
- [ ] 热修补
- [ ] 等等

## 模板功能使用
支持快速生成页面相关的基类。
1. 下载目录template下所有文件。
2. 放入AndroidStudio 模板路径。

    Windows：AS安装目录/plugins/android/lib/templates/gradle-projects
    
    Mac：/Applications/Android Studio.app/Contents/plugins/android/lib/templates/gradle-projects
    
3. 重启AndroidStudio。
4. 鼠标右键 —— New —— AndroidMVVM —— Base基类，填写对应的参数。
5. 可根据个人习惯进行调整。修改BaseActivity/root/src/app_package文件即可。


## 常用工具
1. [阿里巴巴矢量图](https://www.iconfont.cn/)

## 关于我

## 感谢
- [AACHulk模版](https://github.com/madreain/AACHulk)
- [AACHulkTemplate模版](https://github.com/madreain/AACHulkTemplate)
- [MVVMLin](https://github.com/AleynP/MVVMLin)
- [Eyepetizer](https://github.com/VIPyinzhiwei/Eyepetizer)
- [MVVMArchitectureSample](https://github.com/imyyq-star/MVVMArchitectureSample)
- [brick](https://github.com/xiazunyang/brick)
- [Jetpack-MVVM-Best-Practice](https://github.com/KunMinX/Jetpack-MVVM-Best-Practice)
- [More] 借鉴众多开源项目，如有不妥之处，不吝赐教。

## License

```
   Copyright [2020] yangbw

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```