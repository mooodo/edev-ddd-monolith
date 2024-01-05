<template>
  <div>
    <template slot="body" slot-scope="scope">
      {{number_format(value, 2, ".", ",")}}
    </template>
  </div>
</template>
<script>
export default {
  props: {
    value: {
      type: String
    }
  },
  metheds: {
    number_format(number, decimals, decPoint, thousandsSep) {
      number = (number + '').replace(/[^0-9+-Ee.]/g, '')
      var n = !isFinite(+number) ? 0 : +number
      var prec = !isFinite(+decimals) ? 0 : Math.abs(decimals)
      var sep = (typeof thousandsSep === 'undefined') ? ',' : thousandsSep
      var dec = (typeof decPoint === 'undefined') ? '.' : decPoint
      var s = ''
      var toFixedFix = function (n, prec) {
        var k = Math.pow(10, prec)
        return '' + Math.ceil(n * k) / k
      }

      s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.')
      var re = /(-?\d+)(\d{3})/
      while (re.test(s[0])) {
        s[0] = s[0].replace(re, '$1' + sep + '$2')
      }

      if ((s[1] || '').length < prec) {
        s[1] = s[1] || ''
        s[1] += new Array(prec - s[1].length + 1).join('0')
      }
      return s.join(dec)
    }
  }
}
</script>

<style>
</style>
