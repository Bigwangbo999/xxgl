<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="100px"
    >
      <el-form-item label="开票名称" prop="kpmc" label-width="80px">
        <el-input
          v-model="queryParams.kpmc"
          placeholder="请输入"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="税号" prop="taxNo">
        <el-input
          v-model="queryParams.taxNo"
          placeholder="请输入"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="付款账号名称" prop="payer">
        <el-input
          v-model="queryParams.payer"
          placeholder="请输入"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item
        label="付款账号后6位"
        prop="payerAccount"
        label-width="120px"
      >
        <el-input
          v-model="queryParams.payerAccount"
          placeholder="请输入付款账号后6位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="addressPhone" label-width="80px">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" label-width="80px">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button
          type="primary"
          icon="el-icon-refresh"
          size="mini"
          @click="resetQuery"
          >重置</el-button
        >
        <el-button
          type="primary"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:info:export']"
          >导出
        </el-button>
        <el-button
          type="primary"
          icon="el-icon-download"
          size="mini"
          @click="handleExportAll"
          >导出当页
        </el-button>
      </el-form-item>
    </el-form>

    <el-table
      v-loading="loading"
      :data="infoList"
      :max-height="tableHeight"
      @selection-change="handleSelectionChange"
    >
      <!--      <el-table-column label="银行流水号" width="80" v-show="false" align="center" prop="bankSerialNum"/>-->
      <el-table-column label="发票类型" align="center" prop="fplx">
        <template slot-scope="scope">
          <span v-if="scope.row.fplx == 'p'">普通发票（电子）</span>
          <span v-if="scope.row.fplx == 'c'">普通发票（纸质）</span>
          <span v-if="scope.row.fplx == 's'">专用发票</span>
        </template>
      </el-table-column>
      <el-table-column label="付款账号名称" align="center" prop="payer" />
      <el-table-column
        label="付款账号后6位"
        align="center"
        prop="payerAccount"
      />
      <el-table-column label="开票名称" align="center" prop="kpmc" />
      <el-table-column label="开票税号" align="center" prop="taxNo" />
      <el-table-column label="手机号" align="center" prop="phone" />
      <el-table-column label="邮箱" align="center" prop="email" />
      <el-table-column label="地址电话" align="center" prop="addressPhone" />
      <el-table-column label="开户行及账号" align="center" prop="bankAccount" />
      <el-table-column label="备注" align="center" prop="remark" />
      <!--<el-table-column label="提交时间" align="center" prop="updateTime"/>-->
    </el-table>

    <pagination
      ref="pagRef"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改客户信息编码库对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="发票类型" prop="fplx">
          <el-input v-model="form.fplx" placeholder="请输入发票类型" />
        </el-form-item>
        <el-form-item label="付款人" prop="payer">
          <el-input v-model="form.payer" placeholder="请输入付款人" />
        </el-form-item>
        <el-form-item label="付款账号后6位" prop="payerAccount">
          <el-input
            v-model="form.payerAccount"
            placeholder="请输入付款账号后6位"
          />
        </el-form-item>
        <el-form-item label="开票名称" prop="kpmc">
          <el-input v-model="form.kpmc" placeholder="请输入开票名称" />
        </el-form-item>
        <el-form-item label="开票税号" prop="taxNo">
          <el-input v-model="form.taxNo" placeholder="请输入开票税号" />
        </el-form-item>
        <el-form-item label="地址电话" prop="addressPhone">
          <el-input v-model="form.addressPhone" placeholder="请输入地址电话" />
        </el-form-item>
        <el-form-item label="开户行及账号" prop="bankAccount">
          <el-input
            v-model="form.bankAccount"
            placeholder="请输入开户行及账号"
          />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInfo } from "@/api/system/userInfo/info";

export default {
  name: "Info",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 客户信息编码库表格数据
      infoList: [],
      // 日期范围
      dateRange: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fplx: null,
        payer: null,
        phone: null,
        payerAccount: null,
        kpmc: null,
        taxNo: null,
        addressPhone: null,
        bankAccount: null,
        email: null,
        createTime: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //table高度
      tableHeight: null,
    };
  },
  mounted() {
    this.getList();
    window.addEventListener("resize", this.handleScroll);
    this.handleScroll();
  },
  methods: {
    /** 查询客户信息编码库列表 */
    getList() {
      this.loading = true;
      listInfo(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.infoList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.dateRange = [];
      this.form = {
        id: null,
        fplx: null,
        payer: null,
        payerAccount: null,
        kpmc: null,
        taxNo: null,
        addressPhone: null,
        bankAccount: null,
        email: null,
        remark: null,
        status: "0",
        createTime: null,
        updateTime: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加客户信息编码库";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getInfo(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改客户信息编码库";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateInfo(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除客户信息编码库编号为"' + ids + '"的数据项？')
        .then(function () {
          return delInfo(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "userInfo/export",
        {
          ...this.queryParams,
        },
        `info_${new Date().getTime()}.xlsx`
      );
    },
    handleExportAll() {
      this.download(
        "userInfo/exportAll",
        {
          ...this.queryParams,
        },
        `info_${new Date().getTime()}.xlsx`
      );
    },
    //监听height高度
    handleScroll() {
      this.tableHeight =
        window.innerHeight -
        this.$refs.queryForm.$el.scrollHeight -
        this.$refs.pagRef.$el.scrollHeight -
        135;
    },
  },
};
</script>
