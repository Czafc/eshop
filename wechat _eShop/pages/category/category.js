// pages/category/category.js
import {GET} from "../../request/index.js";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    //左侧的菜单数据
    leftMenuList:[],
    //右侧的商品数据
    rightContent:[],
    //被点击的左侧菜单
    currentIndex: 0,
    //右侧滚动条距离顶部距离
    rightScrollTop : 0
  },

  //接口的返回数据
  Cates:[],

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    /*先判断本地存储中有没有旧的数据，若没有给直接发送请求
      没有旧数据，直接发送请求
      有旧的数据 同事旧的数据也没有过期，使用存储中的旧数据
    */
   //1.获取本地存储中的数据
   const Cates = wx.getStorageSync('cates');
   //2.判断
   if(!Cates){
     //不存在，去后端获取 
    this.getCates();
   }else{
     //有旧的数据 定义过期时间1天
     if(Date.now() - Cates.time > 1000 * 60 * 60 * 24){
       this.getCates();
     }else{
       this.Cates = Cates.data;
       let leftMenuList = this.Cates.map(v=>v.catName);
      //构造右侧的商品数据
      let rightContent = this.Cates[0].children;
      this.setData({
        leftMenuList,
        rightContent
      })
     }
   }
  },
  //获取分类数据
  getCates(){
    GET({
      url:"/category/categories"
    }).then(res => {
      this.Cates = res.data;
      //把接口的数据存储到本地存储中
      wx.setStorageSync('cates', {time:Date.now(),data:this.Cates});

      //构造左侧的菜单数据
      let leftMenuList = this.Cates.map(v=>v.catName);
      //构造右侧的商品数据
      let rightContent = this.Cates[0].children;
      this.setData({
        leftMenuList,
        rightContent
      })
    })
  },
  //左侧菜单点击事件
  handleItemTap(e){
    //获取被点击标题的索引
    //给data中的currentIdnex赋值
    //渲染右侧商品内容
    const {index} = e.currentTarget.dataset;
    let rightContent = this.Cates[index].children;
    this.setData({
      currentIndex : index,
      rightContent,
      rightScrollTop : 0
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.tabBar();
  },
  tabBar() {
    if (typeof this.getTabBar === 'function' && this.getTabBar()) {
      this.getTabBar().setData({
        selected: 1
      })
    }
},
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})