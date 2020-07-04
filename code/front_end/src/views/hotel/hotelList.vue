<template>
  <div class="hotelList">
    <a-menu class = "menu" v-model="current" mode="horizontal" @click="changeRegion">
      <a-menu-item key="All"> <a-icon type="mail" />所有商圈</a-menu-item>
      <a-menu-item key="XinJieKou"> <a-icon type="appstore" />新街口</a-menu-item>
      <a-menu-item key="JiangNing"><a-icon type="appstore" />江宁</a-menu-item>
      <a-menu-item key="ZhuJiangLu"> <a-icon type="appstore" />珠江路</a-menu-item>
      <a-menu-item key="FuZiMiao"> <a-icon type="appstore" />夫子庙</a-menu-item>
      <a-menu-item key="HeXi"> <a-icon type="appstore" />河西</a-menu-item>
      <a-menu-item key="XianLin"><a-icon type="appstore" />仙林</a-menu-item>
      <a-menu-item key="JiangBei"><a-icon type="appstore" />江北</a-menu-item>
      <a-dropdown placement="bottomCenter" style="margin-left: 100px">
        <a-button style="background: none;margin-bottom: 8px;" icon ="caret-down">{{sortMethod}}</a-button>
        <a-menu slot="overlay">
          <a-menu-item  @click="sort('id')">
            <a-icon type="home"></a-icon>
            系统默认排序
          </a-menu-item>
          <a-menu-item @click="sort('rate')">
            <a-icon type="star"></a-icon>
            酒店评分排序
          </a-menu-item>
          <a-menu-item @click="sort('evaluatorNum')">
            <a-icon type="smile"></a-icon>
            入住人数排序
          </a-menu-item>
        </a-menu>
      </a-dropdown>
    </a-menu>
    <a-layout style="background:none">
        <a-layout-content style="min-width: 800px">
          <a-spin :spinning="hotelListLoading" >
            <div class="card-wrapper">
                <HotelCard :hotel="item" v-for="item in bizRegionHotelList" :key="item.id" @click.native="jumpToDetails(item.id)">
                    <div v-for="item in emptyBox" :key="item.hotelName" class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3"></div>
                </HotelCard>
            </div>
              <a-pagination showQuickJumper :total="bizRegionHotelList.length" :defaultCurrent="1" @change="pageChange"></a-pagination>
          </a-spin>
      </a-layout-content>
    </a-layout>
  </div>
</template>
<script>
import HotelCard from './components/hotelCard'
import { mapGetters, mapActions, mapMutations } from 'vuex'

export default {
  name: 'home',
  components: {
    HotelCard
  },
  data(){
    return{
      emptyBox: [{ name: 'box1' }, { name: 'box2'}, {name: 'box3'}],
      current:['All'],
      bizRegionHotelList:[],
      currentRegion:"All",
    }
  },
  async mounted() {
    await this.getHotelList()
    this.bizRegionHotelList = this.hotelList
    if (this.sortMethod == '系统默认排序'){
      this.sort('id')
    }
    if (this.sortMethod == '酒店评分排序'){
      this.sort('rate')
    }
    if (this.sortMethod == '入住人数排序'){
      this.sort('evaluatorNum')
    }
  },
  computed: {
    ...mapGetters([
      'hotelList',
      'hotelListLoading',
      'sortMethod'
    ]),
  },
  methods: {
    ...mapMutations([
      'set_hotelListParams',
      'set_hotelListLoading',
      'set_sortMethod'
    ]),
    ...mapActions([
      'getHotelList'
    ]),
    sort(type){
      if (type == 'id'){
        this.set_sortMethod('系统默认排序')
      }
      if (type == 'rate'){
        this.set_sortMethod('酒店评分排序')
      }
      if (type == 'evaluatorNum'){
        this.set_sortMethod('入住人数排序')
      }
      this.bizRegionHotelList.sort(this.compare(type))
    },
    compare(attr) {
      return function(a,b){
        var val1 = a[attr];
        var val2 = b[attr];
        return val2 - val1;
      }
    },
    pageChange(page, pageSize) {
      const data = {
        pageNo: page - 1
      }
      this.set_hotelListParams(data)
      this.set_hotelListLoading(true)
      this.getHotelList()
    },
    jumpToDetails(id){
      this.$router.push({ name: 'hotelDetail', params: { hotelId: id }})
    },
    changeRegion(region) {
      this.currentRegion = region.key
      if (region.key == "All") {
        this.bizRegionHotelList = this.hotelList
      }
      else {
        this.bizRegionHotelList = []
        for (var i = 0; i < this.hotelList.length; i++) {
          if (this.hotelList[i].bizRegion == region.key) {
            this.bizRegionHotelList.push(this.hotelList[i])
          }
        }
      }
    }
  }
}
</script>
<style scoped lang="less">
  .hotelList {
    text-align: center;
    padding: 50px 0;
    .menu{
      margin-bottom: 10px;
      opacity: 1;
      background: none;
    }
    .sort {
    }
    .emptyBox {
      height: 0;
      margin: 50px 50px
    }
    .card-wrapper{
      justify-content: flex-start;
      display:flex;
      flex-wrap: wrap;
      flex-grow: 3;
      min-height: 550px;
      margin-bottom: 10px;
    }
    .card-wrapper .card-item {
      margin: 30px;
      position: relative;
      height: 188px;
    }
  }
</style>