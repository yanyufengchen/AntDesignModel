<template>
  <div class="footer">
    <div class="links">
      <a href="#">中移（成都）信息科技有限公司</a>
      <a href="#">地址：四川省成都市高新区交子大道175号</a>
      <a href="#">联系我们: 400-820-8988</a>
    </div>
    <div class="copyright">
      Copyright <a-icon type="copyright" /> <span>2012 - 2018 Tencent. All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'GlobalFooter',
  data () {
    return {}
  }
}
</script>

<style lang="less" scoped>
.footer {
  padding: 0 16px;
  margin: 48px 0 24px;
  text-align: center;

  .links {
    margin-bottom: 8px;

    a {
      color: rgba(0, 0, 0, 0.45);

      &:hover {
        color: rgba(0, 0, 0, 0.65);
      }

      &:not(:last-child) {
        margin-right: 40px;
      }
    }
  }
  .copyright {
    color: rgba(0, 0, 0, 0.45);
    font-size: 14px;
  }
}
</style>
