<template>
  <div>
    <Dialog ref="d1"
        :title="'商品信息详情'"
        :dialogStyle="{width:'380px',height:'440px'}"
        :modal="true"
        :draggable="true">
      <div style="margin-bottom: 10px;height: 85%;">
        <Form :model="product" class="submit-form">
          <div class="form-item">
            <Label for="id" align="top">商品编号：</Label>
            <TextBox inputId="id" v-model="product.id"></TextBox>
          </div>
          <div class="form-item">
            <Label for="classify" align="top">分类：</Label>
            <TextBox inputId="classify" v-model="product.classify"></TextBox>
          </div>
          <div class="form-item" style="width: 350px;">
            <Label for="name" align="top">商品名称：</Label>
            <TextBox inputId="name" v-model="product.name" style="width: 100%;"></TextBox>
          </div>
          <div class="form-item">
            <Label for="price" align="top">价格：</Label>
            <NumberBox inputId="price" v-model="product.price" :precision="2"
              :groupSeparator="','" :decimalSeparator="'.'"></NumberBox>
          </div>
          <div class="form-item">
            <Label for="originalPrice" align="top">原始价格：</Label>
            <NumberBox inputId="originalPrice" v-model="product.originalPrice" :precision="2"
              :groupSeparator="','" :decimalSeparator="'.'"></NumberBox>
          </div>
          <div class="form-item">
            <Label for="unit" align="top">单位：</Label>
            <TextBox inputId="unit" v-model="product.unit"></TextBox>
          </div>
          <div class="form-item">
            <Label for="supplier" align="top">供应商：</Label>
            <ComboBox inputId="supplier" :data="supplierList" v-model="product.supplierId"
             :editable="false" :panelStyle="{height:'auto'}">
            </ComboBox>
          </div>
          <div class="form-item">
            <Label for="image" align="top">图片：</Label>
            <TextBox inputId="image" v-model="product.image"></TextBox>
          </div>
          <div class="form-item">
            <Label for="tip" align="top">提示：</Label>
            <TextBox inputId="tip" v-model="product.tip"></TextBox>
          </div>
        </Form>
      </div>
      <div class="dialog-button" style="border-width: 0px;">
        <LinkButton style="width:80px" @click="save(product)">保存</LinkButton>
        <LinkButton style="width:80px" @click="remove(product)">删除</LinkButton>
        <LinkButton style="width:80px" @click="closeWindow()">关闭</LinkButton>
      </div>
    </Dialog>
  </div>
</template>

<script>
export default {
  mounted: function() {
    this.getSupplierList()
  },
  props: {
    product: {
      type: Object
    }
  },
  data() {
    return {
      profix: '/api',
      profixOfSupplier: '/api',
      classifyList: [],
      supplierList: []
    }
  },
  methods: {
    openWindow() {
      this.$refs.d1.open()
    },
    closeWindow() {
      this.$refs.d1.close()
    },
    save(product) {
      this.$axios({
        url: this.$data.profix + '/orm/product/saveProduct',
        method: 'post',
        data: product
      }).then(rep => {
        this.$messager.alert({
          title: 'Save',
          icon: 'info',
          msg: '数据保存成功！'
        })
        this.closeWindow()
      }).catch(err => {
        this.$messager.alert({
          title: 'Error',
          icon: 'error',
          msg: '数据保存失败！'
        })
        console.info(err)
      })
    },
    remove(product) {
      if (!product.id || product.id === null || product.id === '') return
      this.$axios.get(this.$data.profix + '/orm/product/deleteProduct', {
        params: { id: product.id }
      }).then(rep => {
        this.$messager.alert({
          title: 'Delete',
          icon: 'info',
          msg: '数据已经删除！'
        })
        this.closeWindow()
      }).catch(err => {
        this.$messager.alert({
          title: 'Error',
          icon: 'error',
          msg: '数据删除失败！'
        })
        console.info(err)
      })
    },
    getSupplierList() {
      this.$axios.get(this.$data.profixOfSupplier + '/orm/supplier/getAll').then(rep => {
        for (var i = 0; i < rep.data.length; i++) {
          this.$data.supplierList[i] = {
            value: rep.data[i]['id'],
            text: rep.data[i]['name']
          }
        }
      }).catch(err => {
        this.$messager.alert({
          title: 'Error',
          icon: 'error',
          msg: '供应商查询失败！'
        })
        console.info(err)
      })
    }
  }
}
</script>
<style>
  .submit-form {
    margin-top: 10px;
    margin-left: 10px;
    text-align: center;
  }
  .form-item {
    margin-bottom:5px;
    margin-right:5px;
    display:inline-block;
  }
</style>
