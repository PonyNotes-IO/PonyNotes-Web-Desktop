<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="版本名称" prop="versionName">
        <el-input v-model="queryParams.versionName" placeholder="请输入版本名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="系统类型" prop="systemType">
        <el-input v-model="queryParams.systemType" placeholder="请输入系统类型" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="状态" clearable style="width: 240px">
          <el-option label="正常" value="正常" />
          <el-option label="停用" value="停用" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="list" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="版本名称" align="center" prop="versionName" />
      <el-table-column label="系统类型" align="center" prop="systemType" />
      <el-table-column label="版本代码" align="center" prop="versionCode" />
      <el-table-column label="更新描述" align="center" prop="updateDesc" :show-overflow-tooltip="true" />
      <el-table-column label="下载URL" align="center" prop="downloadUrl" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <span>{{ scope.row.status }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          <el-button
            v-if="scope.row.status === '正常'"
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleDisable(scope.row)"
          >停用</el-button>
          <el-button
            v-else
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleEnable(scope.row)"
          >启用</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="版本名称" prop="versionName">
              <el-input v-model="form.versionName" placeholder="请输入版本名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="系统类型" prop="systemType">
              <el-input v-model="form.systemType" placeholder="请输入系统类型" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="版本代码" prop="versionCode">
              <el-input v-model="form.versionCode" placeholder="请输入版本代码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="更新描述" prop="updateDesc">
              <el-input v-model="form.updateDesc" type="textarea" placeholder="请输入更新描述" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="下载URL" prop="downloadUrl">
              <el-input v-model="form.downloadUrl" placeholder="请输入下载URL" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态">
                <el-option label="正常" value="正常"></el-option>
                <el-option label="停用" value="停用"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAppVersion, getAppVersion, addAppVersion, updateAppVersion, delAppVersion, disableAppVersion, enableAppVersion } from "@/api/client/appVersion";

export default {
  name: "AppVersion",
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
      // 表格数据
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        versionName: null,
        systemType: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        versionName: [
          { required: true, message: "版本名称不能为空", trigger: "blur" }
        ],
        systemType: [
          { required: true, message: "系统类型不能为空", trigger: "blur" }
        ],
        versionCode: [
          { required: true, message: "版本代码不能为空", trigger: "blur" }
        ]
      },
      // 表格列显示控制
      columns: [
        { key: "id", label: "ID", visible: true },
        { key: "versionName", label: "版本名称", visible: true },
        { key: "systemType", label: "系统类型", visible: true },
        { key: "versionCode", label: "版本代码", visible: true },
        { key: "updateDesc", label: "更新描述", visible: true },
        { key: "downloadUrl", label: "下载URL", visible: true },
        { key: "status", label: "状态", visible: true },
        { key: "createTime", label: "创建时间", visible: true }
      ]
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      listAppVersion(this.queryParams).then(response => {
        this.list = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        versionName: null,
        systemType: null,
        versionCode: null,
        updateDesc: null,
        downloadUrl: null,
        status: "正常"
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加应用版本";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0]
      getAppVersion(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改应用版本";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAppVersion(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAppVersion(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除选中的数据项？').then(function() {
        return delAppVersion(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 停用按钮操作 */
    handleDisable(row) {
      this.$modal.confirm('是否确认停用？').then(function() {
        return disableAppVersion(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("停用成功");
      }).catch(() => {});
    },
    /** 启用按钮操作 */
    handleEnable(row) {
      this.$modal.confirm('是否确认启用？').then(function() {
        return enableAppVersion(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("启用成功");
      }).catch(() => {});
    }
  }
};
</script>
