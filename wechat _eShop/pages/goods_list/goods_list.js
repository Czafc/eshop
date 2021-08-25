// pages/goods_list/goods_list.js
import {GET} from "../../request/index.js";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    tabs:[{
        id:0,
        value:"综合",
        isActive:true
      },{
        id:1,
        value:"销量",
        isActive:false
      },{
        id:2,
        value:"价格",
        isActive:false
      }
    ],
    hasNoData : false,
    goodsList:[]
  },
  //向接口传递的参数
  QueryParams:{
    query:"",
    cid:"",
    pageNum:1,
    pageSize:10,
    sort:0 //商品列表排序方式
  },
  totalPages:1,
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.QueryParams.cid = options.cid;
    this.QueryParams.query = options.query;
    this.getGoodsList();
  },
  //获取商品列表
  getGoodsList(){
    let params = {
      pageNum : this.QueryParams.pageNum,
      pageSize : this.QueryParams.pageSize,
      sort : this.QueryParams.sort
    }
    if(this.QueryParams.cid) {
      params.cid = this.QueryParams.cid;
    }else if(this.QueryParams.query) {
      params.query = this.QueryParams.query;
    }
    GET({url:"/product/search",data : params})
    .then(result=>{
      const total = result.data.total;
      if(total == 0) {
        this.setData({
          hasNoData : true
        })
        return;
      }
      //计算总页数
      this.totalPages = Math.ceil(total/this.QueryParams.pageSize);
      this.setData({
        //拼接数组
        goodsList : [...this.data.goodsList, ...result.data.productList]
      });
      //关闭下拉刷新效果
      wx.stopPullDownRefresh();
    })
  },
  handleTabsItemChange(e){
    //console.log(e);
    const {index} = e.detail;
    let {tabs} = this.data;
    tabs.forEach((v,i)=>i===index?v.isActive=true:v.isActive=false);
    this.setData({
      tabs
    })
    //按照新的排序依据获取商品列表
    this.QueryParams.sort = index;
    this.QueryParams.pageNum = 1;
    this.setData({
      goodsList : []
    })
    this.getGoodsList();
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    //重置结果数组
    this.setData({
      goodsList : []
    })
    //重置页码
    this.QueryParams.pageNum = 1;
    this.getGoodsList();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    //触底获取下一页数据
    //判断还有没有下一页数据
    if(this.QueryParams.pageNum >= this.totalPages) {
      //没有下一页数据
      wx.showToast({title: '已经到底了...'})
    }else {
      //有下一页数据
      this.QueryParams.pageNum ++;
      this.getGoodsList();
    }
  }
})