// pages/collection/collection.js
import {GET} from "../../request/index.js";
import {POST} from "../../request/index.js";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    type : -1,
    goodsList : [],
    storeList : []
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    let userId = wx.getStorageSync('user').userId;
    if(!userId) {
      wx.showToast({
        title: '请先登录',
        icon : 'none',
        success(res) {
          setTimeout(wx.navigateBack,1000);
        }
      })
    }else {
      let pages = getCurrentPages();
      let currentPage = pages[pages.length - 1];
      let type = currentPage.options.type;
      GET({
        url : "/record/getAll",
        data : {
          type,
          userId
        }
      }).then(result => {
        this.setData({
          type,
          goodsList : result.data
        })
      })
    }
  },

  onReady : function() {
    setTimeout(this.setTitle,500);
  },

  setTitle(){
    let type = this.data.type;
    if(type == 0) {
      wx.setNavigationBarTitle({
        title: '收藏的店铺'
      })
    }else if(type == 1) {
      wx.setNavigationBarTitle({
        title: '收藏的商品'
      })
    }else if(type == 2) {
      wx.setNavigationBarTitle({
        title: '关注的商品'
      })
    }else if(type == 3) {
      wx.setNavigationBarTitle({
        title: '历史足迹'
      })
    }
  },

  handleDelete(e) {
    const that = this;
    let index = e.currentTarget.dataset.index;
    wx.showModal({
      title : "提示",
      content : "删除此条记录？",
      success(res)  {
        if(res.confirm) {
          GET({
            url : "/record/delete",
            data : {
              type : that.data.type,
              itemId : that.data.goodsList[index].itemId
            }
          }).then(result => {
            if(result.data.result === "success") {
              wx.showToast({
                title: '删除成功'
              })
              let goodsList = that.data.goodsList;
              goodsList.splice(index,1);
              that.setData({
                goodsList
              })
            }
            else{
              wx.showToast({
                title: '删除失败',
                icon : 'none'
              })
            }
          })
        }
      }
    })
  },

  clearHistory(){
    let that = this;
    wx.showModal({
      title : '确定清空历史记录？',
      success (res) {
        if (res.confirm) {
          let userId = wx.getStorageSync('user').userId;
          GET({url : '/record/clearHistory', data : {userId}})
          .then(result => {
            if(result.data.result === "success") {
              that.setData({
                goodsList : []
              })
              that.setTitle();
            }else {
              wx.showToast({
                title: '删除失败',
                icon : 'none'
              })
            }
          })
        }
      }
    })
  }
})