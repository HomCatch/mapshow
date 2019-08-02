<template>
  <div class="upload-wap">
    <input id="File" type="file" style="display:none" :ref="fileName" :accept="accept" @change="upload($event)" />
    <el-button :loading="btnLoading" size="mini" style="width:100px" type="primary" @click="chooseFile(fileName)">{{btnText}}</el-button>
  </div>
</template>
<script>
export default {
  props: {
    fileName: {
      default: "fileInput"
    },
    accept: {
      default: "image/jpeg,image/jpg,image/png"
    },
    btnText: {
      default: "导入"
    },
    multiple: {
      default: false,
      type: Boolean
    },
    width: {
      default: 1200,
      type: Number
    },
    height: {
      default: 679,
      type: Number
    },
    checkImg: {
      default: true,
      type: Boolean
    }
  },
  data() {
    return {
      btnLoading: false
    };
  },
  methods: {
    chooseFile(i) {
      this.$refs[i].click();
    },
    async upload(obj) {
      console.log(obj)
      //导入
      let files = Array.from(obj.target.files); // 选择的文件
      let acceptArr = this.accept.split(",");   // 规定的文件格式
      // 检测是否有有效文件
      if (!obj.target.files) {
        this.$message({
          type: "error",
          message: "请先选择文件"
        });
        this.$refs[this.fileName].value = "";
        return;
      }
      // 文件类型是否正确
      let flag = files.every(item => {
        console.log(item)
        console.log(acceptArr)
        return acceptArr.includes(item.type);
      });
      // if (!flag) {
      //   this.$message({
      //     type: "error",
      //     message: "文件类型不符合要求"
      //   });
      //   this.$refs[this.fileName].value = "";
      //   return;
      // }
      let formData = new FormData();
      files.forEach((item, index) => {
        formData.append(`file`, item);
      });
      this.$emit("_import", formData);
      this.$refs[this.fileName].value = "";
    }
  }
};
</script>
<style lang="scss">
.upload-wap {
  width: 100px;
}
</style>
