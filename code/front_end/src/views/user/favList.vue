<template>
  <div class="favList">
    <a-layout style="background: none">
      <a-layout-content style="min-width: 800px;">
        <a-spin :spinning=false v-if="favList.length>0">
          <div class="card-wrapper">
            <HotelCard :hotel="item" v-for="item in favList" :key="item.index" @click.native="jumpToDetails(item.id)">
              <div v-for="item in emptyBox" :key="item.name" class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3"></div>
            </HotelCard>
          </div>
          <a-pagination showQuickJumper :total="favList.length" :defaultCurrent="1" @change="pageChange"></a-pagination>
        </a-spin>
        <p v-if="favList.length==0" style="margin-top: 20px;font-size: large">收藏夹里什么都没有哦！<br><br>快去挑选你心仪的酒店吧！</p>
      </a-layout-content>
    </a-layout>
  </div>
</template>
<script>
  import HotelCard from '../hotel/components/hotelCard'
  import { mapGetters, mapActions, mapMutations } from 'vuex'

  export default {
    name: 'favList',
    components: {
      HotelCard
    },
    data(){
      return{
        emptyBox: [{ name: 'box1' }, { name: 'box2'}, {name: 'box3'}]
      }
    },
    async mounted() {
      await this.getFavList()
    },
    computed: {
      ...mapGetters([
        'favList',
        'favListLoading'
      ])
    },
    methods: {
      ...mapMutations([
        'set_FavListParams',
        'set_FavListLoading'
      ]),
      ...mapActions([
        'getFavList',
        'getFavListLoading'
      ]),

      pageChange(page, pageSize) {
        const data = {
          pageNo: page - 1
        };
        this.set_FavListParams(data);
        this.set_FavListLoading(true);
        this.getFavList()
      },
      jumpToDetails(id){
        this.$router.push({ name: 'hotelDetail', params: { hotelId: id }})
      }
    }
  }
</script>
<style scoped lang="less">
  .favList {
    text-align: center;
    padding: 50px 0;
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