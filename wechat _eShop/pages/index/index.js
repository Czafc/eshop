// index.js
// 获取应用实例
//引入用来发送请求的方法
import {GET} from "../../request/index.js";

const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    //轮播图数组
    swiperList : [],
    catesList:[],
    floorList:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  
  onLoad: function (options) {
    this.getSwiperList();
    this.getCatitems();
    this.getFloorList();
  },
  getSwiperList(){
    //发送异步请求获取轮播图
    GET({url:"/index/carouselPics"})
    .then(result=>{
      this.setData({
        swiperList:result.data
      })
    })
  },

  getCatitems(){
    //发送异步请求获取导航菜单
    GET({url:"/index/navigatorIcons"})
    .then(result=>{
      this.setData({
        catesList:result.data
      })
    })
  },
  //获取楼层数据
  getFloorList(){
    //发送异步请求获取楼层信息
    GET({url:"/index/floors"})
    .then(result=>{
      this.setData({
        floorList:result.data
      })
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
        selected: 0
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