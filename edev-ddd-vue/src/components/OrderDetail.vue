<template>
  <div>
    <div v-for="(orderItem, index) in orderItems" :key="index" style="vertical-align: middle;">
      <div class="item f-row">
        <div><img class="item-img" v-if="orderItem.product.image" :src="orderItem.product.image"></div>
        <div class="f-column">
          <div class="item-desp f-full">
            <label style="margin-left: 20px;">产品：{{orderItem.product.name}}</label>
          </div>
          <div class="item-desp f-full">
            <label style="margin-left: 20px;">数量：{{orderItem.quantity}}</label>
            <label style="margin-left: 20px;">单价：{{orderItem.price}}</label>
            <label style="margin-left: 20px;">金额：{{orderItem.amount}}</label>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    orderItems: {
      type: Array
    }
  }
}
</script>

<style>
  .item {
    height: 60px;
    margin: 6px 30px;
    text-align: left;
  }
  .item-img {
    width: 60px;
    height: 60px;
  }
  .item-desp {
    line-height: 30px;
  }
</style>
