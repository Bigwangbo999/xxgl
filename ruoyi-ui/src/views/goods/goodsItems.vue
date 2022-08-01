<template>
  <div>
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      style="margin-top: 20px; margin-left: 20px"
    >
      <el-form-item label="商品名称" prop="spmc">
        <el-input
          v-model="queryParams.spmc"
          placeholder="请输入商品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="税收分类编码" prop="ssflbp">
        <el-input
          v-model="queryParams.ssflbp"
          placeholder="请输入税收分类编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品编码" prop="spbmjc">
        <el-input
          v-model="queryParams.spbmjc"
          placeholder="请输入税收分类编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否含税" prop="isTax">
        <template>
          <el-select v-model="queryParams.isTax" clearable placeholder="请选择">
            <el-option
              v-for="item in searchIsTax"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </template>
      </el-form-item>
      <el-form-item label="是否享受优惠政策" prop="taxPre">
        <el-select
          v-model="queryParams.taxPre"
          placeholder="请选择"
          clearable
          @keyup.enter.native="handleQuery"
        >
          <el-option
            v-for="item in searchYesOrNo"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
        <el-button
          type="primary"
          size="mini"
          @click="
            showExcelUploadFlag = true;
            fileList = [];
          "
          >导入</el-button
        >
        <el-button type="primary" size="mini" @click="inserInfo"
          >新增</el-button
        >
        <el-button type="primary" size="mini" @click="deleteAll"
          >批量删除</el-button
        >
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      :max-height="tableHeight"
      @selection-change="checkRow"
    >
      <el-table-column type="selection" width="55" fixed="left" align="center">
      </el-table-column>
      <el-table-column
        prop="spmc"
        label="商品名称"
        fixed="left"
        :show-overflow-tooltip="true"
        width="160"
      ></el-table-column>
      <el-table-column
        prop="spbmjc"
        label="商品编码"
        :show-overflow-tooltip="true"
        width="160"
      ></el-table-column>
      <el-table-column
        prop="ssflbp"
        label="税收分类编码"
        :show-overflow-tooltip="true"
        width="160"
      ></el-table-column>
      <el-table-column
        prop="spect"
        label="规格型号"
        :show-overflow-tooltip="true"
        width="160"
      ></el-table-column>
      <el-table-column
        prop="unit"
        label="单位"
        :show-overflow-tooltip="true"
        width="160"
      ></el-table-column>
      <el-table-column
        prop="price"
        label="不含税单价"
        :show-overflow-tooltip="true"
        width="160"
      ></el-table-column>
      <el-table-column
        prop="taxPrice"
        label="含税单价"
        :show-overflow-tooltip="true"
        width="160"
      ></el-table-column>
      <el-table-column
        prop="taxrate"
        label="税率"
        :show-overflow-tooltip="true"
        width="160"
      ></el-table-column>
      <!--      <el-table-column prop="zeroTax" label="零税率" :show-overflow-tooltip="true" width="160">-->
      <!--        <template slot-scope="scope">-->
      <!--          <span>{{getZeroTax(scope.row.zeroTax)}}</span>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <el-table-column
        prop="taxPre"
        label="是否享受优惠政策"
        :show-overflow-tooltip="true"
        width="160"
      >
        <template slot-scope="scope">
          <span>{{ getTaxPre(scope.row.taxPre) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="taxPreCon"
        label="优惠政策内容"
        :show-overflow-tooltip="true"
        width="160"
      ></el-table-column>
      <el-table-column label="操作" width="120" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="updateInfo(scope.row)"
            >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            style="color: red"
            @click="deleteGoodsInfo(scope.row.id)"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="导入信息"
      :visible.sync="showExcelUploadFlag"
      :close-on-click-modal="false"
      top="8vh"
      class="customDia"
      width="450px"
      append-to-body
      @close="showFlag = false"
    >
      1、下载
      <a
        href="#"
        @click="currencyExcelDownLoad"
        style="text-decoration: none; color: #409eff"
        >《商品信息模板》</a
      ><br /><br />
      2、打开下载模板,将商品信息填入,上限1000条<br /><br />
      3、点击“选择文件”按钮,选择处理好的文件<br /><br />
      4、选择要导入的商品信息,点击“导入”<br /><br />
      <el-upload
        class="upload-demo"
        ref="upload"
        action=""
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        accept=".xls,.xlsx"
        :file-list="fileList"
        :auto-upload="false"
        :on-success="finishUploadSus"
        :on-error="finishUploaderr"
        :show-file-list="true"
        :on-change="onchangeFile"
        :multiple="false"
        :limit="1"
        :on-exceed="handleExceed"
        :http-request="httpRequest"
        size="small"
      >
        <el-button
          slot="trigger"
          style="background-color: rgb(255, 255, 255)"
          size="mini"
          >选择文件</el-button
        >
        <div slot="tip" class="el-upload__tip">已选择的文件：</div>

        <el-button
          :loading="uploadExcelLoading"
          :disabled="submitUploadFlag"
          style="
            margin-left: 10px;
            background-color: rgb(0, 143, 215);
            color: rgb(255, 255, 255);
          "
          size="mini"
          @click="submitUpload"
          >导入
        </el-button>
      </el-upload>
      <div v-if="showFlag">
        <span style="color: #ff1f1f">导入失败{{ errSize }}条</span><br />
        <span style="color: #61ff4d">导入成功{{ successSize }}条</span><br />
        <el-button
          type="primary"
          style="background-color: rgb(0, 143, 215)"
          size="mini"
          @click="openPreview"
          >预览
        </el-button>
      </div>
    </el-dialog>
    <pagination
      v-show="total > 0"
      ref="pagRef"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <el-dialog
      :title="this.title"
      :visible.sync="insetDialangFlag"
      width="50%"
      @close="closeInsertDialog"
    >
      <el-form
        :rules="rules"
        :inline="true"
        :model="goodsEntity"
        ref="insertGoodForm"
        class="demo-form-inline"
        label-width="110px"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="商品编码" prop="spbmjc">
              <el-input
                v-model="goodsEntity.spbmjc"
                placeholder="请输入"
                maxlength="30"
                style="width: 200px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单价" prop="price">
              <el-input
                v-model="goodsEntity.price"
                placeholder="请输入"
                maxlength="30"
                style="width: 200px"
              />
              <!--              <treeselect v-model="goodsEntity.price" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />-->
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="商品名称" prop="spmc">
              <el-input
                v-model="goodsEntity.spmc"
                placeholder="请输入"
                maxlength="30"
                style="width: 200px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="含税价标识" prop="isTax">
              <el-select
                v-model="goodsEntity.isTax"
                placeholder="请选择"
                clearable
                @change="dutyFree"
                style="width: 200px"
              >
                <el-option
                  v-for="item in isTax"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="税收分类编码" prop="ssflbp">
              <el-input
                v-model="goodsEntity.ssflbp"
                placeholder="请输入"
                maxlength="30"
                style="width: 200px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="规格型号" prop="spect">
              <el-input
                v-model="goodsEntity.spect"
                placeholder="请输入"
                maxlength="30"
                style="width: 200px"
              />
              <!--              <el-select v-model="goodsEntity.spect" placeholder="请选择" clearable>-->
              <!--              </el-select>-->
              <!--              <treeselect v-model="goodsEntity.price" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />-->
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="单位" prop="unit">
              <el-input
                v-model="goodsEntity.unit"
                placeholder="请输入"
                maxlength="30"
                style="width: 200px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="享受优惠政策" prop="taxPre">
              <!--              <el-input v-model="goodsEntity.taxPre" placeholder="请输入" maxlength="30"/>-->
              <!--              <treeselect v-model="goodsEntity.price" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />-->

              <el-select
                v-model="goodsEntity.taxPre"
                placeholder="请选择"
                clearable
                style="width: 200px"
              >
                <el-option
                  v-for="item in yesOrNo"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="税率" prop="taxrate">
              <el-select
                v-model="goodsEntity.taxrate"
                placeholder="税率"
                clearable
                style="width: 200px"
              >
                <el-option
                  v-for="item in taxList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="优惠政策类型" prop="taxPreCon">
              <el-select
                v-model="goodsEntity.taxPreCon"
                placeholder="请选择"
                clearable
                style="width: 200px; right: auto"
              >
                <el-option
                  v-for="item in taxPreCon"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
              <!--              <el-input v-model="goodsEntity.taxPre" placeholder="请输入" maxlength="30" text-align="right"/>-->
              <!--              <treeselect v-model="goodsEntity.price" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />-->
            </el-form-item>
          </el-col>
        </el-row>
        <el-row> </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="insertInfo">确 定</el-button>
        <el-button @click="closeInsertDialog">取 消</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="导入失败原因"
      @close="closePreview"
      :visible.sync="preview"
    >
      <el-table :data="errResults">
        <el-table-column prop="errMsg" label="错误原因"> </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import {
  getList,
  insertGoods,
  updateGoods,
  deleteGoods,
} from "../../views/../api/goods/goods.js";
import { importFile } from "@/api/import/import";

export default {
  name: "goodsItems",
  data() {
    return {
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      //成功条数
      successSize: 0,
      //失败条数
      errSize: 0,
      showFlag: false,
      //预览弹窗
      preview: false,
      errResults: [],
      //新增或者修改
      title: "",
      tableHeight: null,
      taxList: [
        {
          value: 0.17,
          label: "17%",
        },
        {
          value: 0.16,
          label: "16%",
        },
        {
          value: 0.13,
          label: "13%",
        },
        {
          value: 0.1,
          label: "10%",
        },
        {
          value: 0.06,
          label: "6%",
        },
        {
          value: 0.05,
          label: "5%",
        },
        {
          value: 0.04,
          label: "4%",
        },
        {
          value: 0.03,
          label: "3%",
        },
        {
          value: 0.015,
          label: "1.5%",
        },
        {
          value: 0.01,
          label: "1%",
        },
        {
          value: 0,
          label: "0%",
        },
      ],
      searchYesOrNo: [
        {
          value: 1,
          label: "是",
        },
        {
          value: 0,
          label: "否",
        },
      ],
      searchIsTax: [
        {
          value: 1,
          label: "含税",
        },
        {
          value: 0,
          label: "不含税",
        },
      ],
      yesOrNo: [
        {
          value: 1,
          label: "是",
        },
        {
          value: 0,
          label: "否",
        },
      ],
      isTax: [
        {
          value: 1,
          label: "是",
        },
        {
          value: 0,
          label: "否",
        },
      ],
      taxPreCon: [
        {
          value: "免税",
          label: "免税",
        },
        {
          value: "不征税",
          label: "不征税",
        },
      ],
      currentRow: [],
      insetDialangFlag: false,
      dataList: [],
      total: 0,
      showExcelUploadFlag: false,
      submitUploadFlag: false,
      fileList: [],
      uploadExcelLoading: false,
      goodsEntity: {
        id: null,
        ssflbp: null,
        spmc: null,
        spbmjc: null,
        ssflbmbbh: null,
        taxPre: null,
        taxPreCon: null,
        zeroTax: null,
        taxrate: null,
        unit: null,
        spect: null,
        price: null,
        taxPrice: null,
        isTax: null,
      },
      rules: {
        spmc: [{ required: true, message: "商品不能为空", trigger: "blur" }],
        ssflbp: [{ required: true, message: "税收分类编码", trigger: "blur" }],
        isTax: [{ required: true, message: "含税价标识编码", trigger: "blur" }],
        spbmjc: [
          { required: true, message: "商品编码不能为空", trigger: "blur" },
        ],
        taxrate: [{ required: true, message: "税率不能为空", trigger: "blur" }],
        taxPre: [
          {
            required: true,
            message: "请选择是否享受优惠政策",
            trigger: "blur",
          },
        ],
      },
    };
  },
  mounted() {
    this.handleQuery();
    window.addEventListener("resize", this.handleScroll);
    this.handleScroll();
  },
  // //销毁,否则跳到别的路由还是会出现
  destroyed() {
    window.removeEventListener("resize", this.handleScroll);
  },
  methods: {
    handleQuery() {
      getList(this.queryParams).then((res) => {
        this.dataList = res.data.list;
        this.total = res.data.total;
      });
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    //多选事件
    checkRow(currentRow) {
      this.currentRow = currentRow;
      // console.log(this.currentRow)
    },
    getList() {
      getList(this.queryParams).then((res) => {
        this.dataList = res.data.list;
        this.total = res.data.total;
      });
    },
    submitUpload() {
      /*console.log(this.fileList)
        importFile(this.fileList).then(res => {

        })*/

      this.$refs.upload.submit();
    },
    handlePreview(file) {},
    handleRemove(file, fileList) {
      this.submitUploadFlag = true;
    },
    finishUploadSus(response, file) {
      if (response.code == "200") {
        this.$alert("上传成功", "成功提示", {
          confirmButtonText: "确定",
        });
        this.fileList = [];
        this.handleQuery();
        this.submitUploadFlag = true;
      } else {
        this.$alert(response.message, "错误提示", {
          confirmButtonText: "确定",
        });
        this.fileList = [];
        this.submitUploadFlag = true;
      }
      this.uploadExcelLoading = false;
    },
    finishUploaderr(err, file, fileList) {
      this.$alert("上传失败", "错误提示", {
        confirmButtonText: "确定",
      });
      this.fileList = [];
      this.submitUploadFlag = true;
      this.uploadExcelLoading = false;
    },
    onchangeFile(file) {
      if (!file.response) {
        this.submitUploadFlag = false;
      }
    },
    closePreview() {
      this.showFlag = false;
    },
    handleExceed(files, fileList) {
      this.$message.warning(`每次只能上传一个文件！`);
    },
    httpRequest(param) {
      let fileObj = param.file; // 相当于input里取得的files
      let fd = new FormData(); // FormData 对象
      fd.append("file", fileObj); // 文件对象

      let url = "/goods/importExcel";
      let config = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };
      this.loading = true;
      // this.showExcelUploadFlag = false
      importFile(url, fd).then((res) => {
        if (res.code == "200") {
          this.showFlag = true;
          this.errSize = res.data.errTotal;
          this.successSize = res.data.sucTotal;
          this.errResults = res.data.errResults;
          this.$message.success("导入成功！");
          this.loading = false;
        } else if (res.data.code != "200") {
          this.showExcelUploadFlag = false;
          this.loading = false;
          // console.log(res.data.msg)
          this.$message.error(res.data.msg);
        }
        this.getList();
      });
    },
    currencyExcelDownLoad() {
      //通用Excel导入模板下载
      this.$request
        .get("/goods/downloadExcel", { responseType: "blob" })
        .then((res) => {
          if (res) {
            const content = res;
            const blob = new Blob([content]);
            const fileName = "商品信息模板.xlsx";
            if ("download" in document.createElement("a")) {
              // 非IE下载
              const elink = document.createElement("a");
              elink.download = fileName;
              elink.style.display = "none";
              elink.href = URL.createObjectURL(blob);
              document.body.appendChild(elink);
              elink.click();
              URL.revokeObjectURL(elink.href); // 释放URL 对象
              document.body.removeChild(elink);
            } else {
              // IE10+下载
              navigator.msSaveBlob(blob, fileName);
            }
          }
        });
    },
    inserInfo() {
      this.insetDialangFlag = true;
      this.title = "新增商品";
    },
    isEmpty(obj) {
      if (typeof obj === "undefined" || obj == null || obj === "") {
        return true;
      } else {
        return false;
      }
    },
    //免税
    dutyFree() {
      if (!this.isEmpty(this.goodsEntity.taxPreCon)) {
        this.goodsEntity.taxPre = 1;
        this.goodsEntity.taxrate = 0;
        if (this.goodsEntity.taxPreCon == "免税") {
          this.taxList = [
            {
              value: 0,
              label: "免税",
            },
          ];
        } else {
          this.taxList = [
            {
              value: 0,
              label: "不征税",
            },
          ];
        }

        this.yesOrNo = [
          {
            value: 1,
            label: "是",
          },
        ];
      } else {
        if (this.title != "修改商品") {
          this.goodsEntity.taxrate = null;
          this.goodsEntity.taxPre = null;
        }
        this.yesOrNo = [
          {
            value: 1,
            label: "是",
          },
          {
            value: 0,
            label: "否",
          },
        ];
        this.taxList = [
          {
            value: 0.17,
            label: "17%",
          },
          {
            value: 0.16,
            label: "16%",
          },
          {
            value: 0.13,
            label: "13%",
          },
          {
            value: 0.1,
            label: "10%",
          },
          {
            value: 0.06,
            label: "6%",
          },
          {
            value: 0.05,
            label: "5%",
          },
          {
            value: 0.04,
            label: "4%",
          },
          {
            value: 0.03,
            label: "3%",
          },
          {
            value: 0.015,
            label: "1.5%",
          },
          {
            value: 0.01,
            label: "1%",
          },
          {
            value: 0,
            label: "0%",
          },
        ];
      }
    },
    //打开预览弹窗
    openPreview() {
      this.preview = true;
    },
    //插入接口
    insertInfo() {
      // console.log(this.goodsEntity);
      if (this.isEmpty(this.goodsEntity.spmc)) {
        this.$message.warning("请输入商品名称");
        return;
      }
      if (this.isEmpty(this.goodsEntity.ssflbp)) {
        this.$message.warning("请输入税收分类编码");
        return;
      }
      if (this.isEmpty(this.goodsEntity.spbmjc)) {
        this.$message.warning("请输入商品编码");
        return;
      }
      if (this.isEmpty(this.goodsEntity.isTax)) {
        this.$message.warning("请选择含税价标识");
        return;
      }
      if (this.isEmpty(this.goodsEntity.taxPre)) {
        this.$message.warning("请选择是否享受优惠");
        return;
      }
      if (this.isEmpty(this.goodsEntity.taxrate)) {
        this.$message.warning("请选择税率");
        return;
      }
      if (
        this.goodsEntity.taxPre == 1 &&
        this.isEmpty(this.goodsEntity.taxPreCon)
      ) {
        this.$message.warning("请勾选优惠内容");
        return;
      } else if (
        this.goodsEntity.taxPre == 0 &&
        !this.isEmpty(this.goodsEntity.taxPreCon)
      ) {
        this.$message.warning("不享受优惠政策，不能勾选优惠内容");
        return;
      }

      if (this.goodsEntity.taxrate == 0 && this.goodsEntity.taxPre == "1") {
        if (this.goodsEntity.taxPreCon == "不征税") {
          this.goodsEntity.zeroTax = 2;
        } else if (this.goodsEntity.taxPreCon == "免税") {
          this.goodsEntity.zeroTax = 1;
        }
      } else {
        this.goodsEntity.zeroTax = 3;
      }
      if (this.goodsEntity.taxrate != "0") {
        this.goodsEntity.zeroTax = "";
      }
      //新增
      if (this.isEmpty(this.goodsEntity.id)) {
        insertGoods(this.goodsEntity).then((res) => {
          if (res.code == "200") {
            this.insetDialangFlag = false;
            this.$refs.insertGoodForm.resetFields();
            this.getList();
          }
        });
      } else {
        //修改
        updateGoods(this.goodsEntity).then((res) => {
          if (res.code == "200") {
            this.$message.success("修改成功");
            this.insetDialangFlag = false;
            this.$refs.insertGoodForm.resetFields();
            this.getList();
          }
        });
      }
      this.taxList = [
        {
          value: 0.17,
          label: "17%",
        },
        {
          value: 0.16,
          label: "16%",
        },
        {
          value: 0.13,
          label: "13%",
        },
        {
          value: 0.1,
          label: "10%",
        },
        {
          value: 0.06,
          label: "6%",
        },
        {
          value: 0.05,
          label: "5%",
        },
        {
          value: 0.04,
          label: "4%",
        },
        {
          value: 0.03,
          label: "3%",
        },
        {
          value: 0.015,
          label: "1.5%",
        },
        {
          value: 0.01,
          label: "1%",
        },
        {
          value: 0,
          label: "0%",
        },
      ];
    },
    updateInfo(row) {
      this.title = "修改商品";
      this.insetDialangFlag = true;
      this.goodsEntity = JSON.parse(JSON.stringify(row));
      this.dutyFree();
    },
    //关闭弹窗
    closeInsertDialog() {
      this.insetDialangFlag = false;
      this.$refs.insertGoodForm.resetFields();
      this.refGoodEntity();
    },
    refGoodEntity() {
      this.goodsEntity.id = null;
      this.goodsEntity.ssflbp = null;
      this.goodsEntity.spmc = null;
      this.goodsEntity.spbmjc = null;
      this.goodsEntity.ssflbmbbh = null;
      this.goodsEntity.taxPre = null;
      this.goodsEntity.taxPreCon = null;
      this.goodsEntity.zeroTax = null;
      this.goodsEntity.taxrate = null;
      this.goodsEntity.unit = null;
      this.goodsEntity.spect = null;
      this.goodsEntity.price = null;
      this.goodsEntity.taxPrice = null;
      this.goodsEntity.isTax = null;
    },
    deleteGoodsInfo(data) {
      let id = [];
      id.push(data);
      this.$confirm("是否删除该商品?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return deleteGoods(id);
        })
        .then((res) => {
          if (res.code == "200") {
            this.$message.success("删除成功");
            this.getList();
          }
        });
    },
    deleteAll() {
      let ids = [];
      this.currentRow.forEach((o) => ids.push(o.id));
      this.$confirm("是否删除所选商品?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return deleteGoods(ids);
        })
        .then((res) => {
          if (res.code == "200") {
            this.$message.success("删除成功");
            this.getList();
          }
        });
    },
    //判断零税率
    getZeroTax(zeroTax) {
      if (zeroTax == "" || zeroTax == null) {
        return "非零税率";
      }
      if (zeroTax == "1") {
        return "免税";
      }
      if (zeroTax == "2") {
        return "不征税";
      }
      if (zeroTax == "3") {
        return "普通零税率";
      }
    },
    getTaxPre(taxPre) {
      if (taxPre == "0" || taxPre == null) {
        return "不享受";
      }
      if (taxPre == "1") {
        return "享受";
      }
    },
    //监听height高度
    handleScroll() {
      this.tableHeight =
        window.innerHeight -
        this.$refs.queryForm.$el.scrollHeight -
        this.$refs.pagRef.$el.scrollHeight -
        130;
    },
  },
};
</script>

<style scoped>
::v-deep .el-dialog__footer {
  display: flex;
  justify-content: center;
}
</style>
