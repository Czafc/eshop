// Components/Tabs/Tabs.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    tabs:{
      type:Array,
      value:[]
    }
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    //点击事件
    handleItemTap(e){
      //获取点击的索引
      const {index}=e.currentTarget.dataset;
      //console.log(index);
      this.triggerEvent("tabsItemChange",{index});
    }
  }
})
