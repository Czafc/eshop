// pages/goods_detail/goods_detail.js
import {GET} from "../../request/index.js";
import {POST} from "../../request/index.js";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    goodsObj:{},
    collected : false
  },
  GoodsInfo:{},
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const {productId} = options;
    let userId = wx.getStorageSync('user').userId;
    if(userId) {
      POST({
        url : "/record/add",
        data : {
          type : 3,
          productId,
          userId : userId
        }
      })
    }
    this.getGoodsDetail(productId);
  },
  getGoodsDetail(productId){
    let params = {
      productId : productId
    }
    let userId = wx.getStorageSync('user').userId;
    if(userId) params.userId = userId;
    GET({url:"/product/productDetail",data : params })
    .then(result=>{
      //console.log(result.data)
      this.GoodsInfo = result.data;
      this.setData({
        goodsObj : {
          productImages : result.data.productImages,
          price : result.data.price,
          name : result.data.productName,
          //iPhone不能识别webp图片格式
          //前端对富文本内容进行简单适配
          productIntroduction : result.data.productIntroduction.replace(/\.webp/g, '.jpg')
        },
        collected : result.data.collected
      })
    })
  },
  //点击轮播图放大预览
  handlePreviewImage(e){
    const urls = this.GoodsInfo.productImages.map(v=>v.picMid);
    const current = e.currentTarget.dataset.url;
    wx.previewImage({
      current,
      urls
    })
  },
  //点击加入购物车
  handleCartAdd(){
    if(!wx.getStorageSync('user').userId) {
      wx.navigateTo({
        url: '/pages/login/login'
      });
      return;
    }
    let userId = wx.getStorageSync('user').userId;
    let productId = this.GoodsInfo.productId;
    let cartData = {
      userId : userId,
      productId : productId,
      num : 1
    }
    //向后台发送数据，将商品加入购物车
    POST({
      url : "/cart/addToCart",
      data : cartData
    }).then(result => {
      if(result.data === "success"){
        wx.showToast({
          title: '加入购物车成功',
        })
      }
    })
  },

  handleBuyNow(){
    if(!wx.getStorageSync('user').userId) {
      wx.navigateTo({
        url: '/pages/login/login'
      });
      return;
    }
      let order = {
        userId : wx.getStorageSync('user').userId,
        totalProductNum : 1,
        totalPrice : this.GoodsInfo.price,
        orderItems : []
      };
      let orderItem = {
        coverImg : this.GoodsInfo.coverImg,
        productName : this.GoodsInfo.productName,
        price : this.GoodsInfo.price,
        productId : this.GoodsInfo.productId,
        cartId : -1,
        productNum : 1
      }
      order.orderItems.push(orderItem);
      wx.setStorageSync('order', order);
      wx.navigateTo({
        url: '/pages/pay/pay'
      })
    },

    handleCollect(){
      let that = this;
      let userId = wx.getStorageSync('user').userId;
      if(!userId) {
        wx.navigateTo({
          url: '/pages/login/login'
        });
        return;
      }
      let collected = !this.data.collected;
      if(collected) {
        POST({url : '/record/add', data : {
          type : 1,
          userId : userId,
          productId : that.GoodsInfo.productId
        }}).then(result => {
          if(result.data.result === "success"){
            wx.showToast({
              title: '收藏成功'
            })
          }else {
            wx.showToast({
              title: '收藏失败',
              icon : 'none'
            })
          }
        })
      }else {
        GET({url : '/record/unCollect', data : {
          userId : userId,
          productId : that.GoodsInfo.productId
        }}).then(result => {
          if(result.data.result === "success"){
            wx.showToast({
              title: '取消收藏成功'
            })
          }else {
            wx.showToast({
              title: '取消收藏失败',
              icon : 'none'
            })
          }
        })
      }
      this.setData({
        collected
      })
    }
})