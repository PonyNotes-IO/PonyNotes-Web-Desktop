<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="版本名称" prop="versionName">
        <el-input
          v-model="queryParams.versionName"
          placeholder="请输入版本名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="版本类型" prop="versionType">
        <el-select v-model="queryParams.versionType" placeholder="请选择版本类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_app_version"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="版本号" prop="versionCode">
        <el-input
          v-model="queryParams.versionCode"
          placeholder="请输入版本号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="系统类型" prop="systemType">
        <el-select v-model="queryParams.systemType" placeholder="请选择系统类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_app_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createdTime">
        <el-date-picker clearable
          v-model="queryParams.createdTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="操作人" prop="operator">
        <el-input
          v-model="queryParams.operator"
          placeholder="请输入操作人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['xmbj:appVersion:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['xmbj:appVersion:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['xmbj:appVersion:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['xmbj:appVersion:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="appVersionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="自增主键" align="center" prop="id" />
      <el-table-column label="版本名称" align="center" prop="versionName" />
      <el-table-column label="版本类型" align="center" prop="versionType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_app_version" :value="scope.row.versionType"/>
        </template>
      </el-table-column>
      <el-table-column label="版本号" align="center" prop="versionCode" />
      <el-table-column label="更新描述" align="center" prop="updateDesc" />
      <el-table-column label="更新描述文件路径" align="center" prop="updateDescFile" width="120">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.updateDescFile"
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handleDownload(scope.row.updateDescFile)"
          >下载</el-button>
        </template>
      </el-table-column>
      <el-table-column label="上传文件包" align="center" prop="packageUrl" width="120">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.packageUrl"
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handleDownload(scope.row.packageUrl)"
          >下载</el-button>
        </template>
      </el-table-column>
      <el-table-column label="系统类型" align="center" prop="systemType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_app_type" :value="scope.row.systemType"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_app_status" :value="String(scope.row.status)"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createdTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作人" align="center" prop="operatorName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['xmbj:appVersion:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['xmbj:appVersion:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改App版本管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="版本名称" prop="versionName">
          <el-input v-model="form.versionName" placeholder="请输入版本名称" />
        </el-form-item>
        <el-form-item label="版本类型" prop="versionType">
          <el-select v-model="form.versionType" placeholder="请选择版本类型">
            <el-option
              v-for="dict in dict.type.sys_app_version"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="版本号" prop="versionCode">
          <el-input v-model="form.versionCode" placeholder="请输入版本号" />
        </el-form-item>
        <el-form-item label="更新描述" prop="updateDesc">
          <el-input v-model="form.updateDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="更新描述文件路径" prop="updateDescFile">
          <file-upload v-model="form.updateDescFile" :file-size="5" :file-type="['png', 'jpg', 'jpeg', 'gif', 'bmp', 'zip', 'rar', '7z', 'doc', 'docx', 'pdf', 'xls', 'xlsx']"/>
        </el-form-item>
        <el-form-item label="上传文件包" prop="packageUrl">
          <file-upload v-model="form.packageUrl" :file-size="200" :file-type="['ipa', 'apk', 'hap', 'dmg', 'exe', 'msi', 'zip']"/>
        </el-form-item>
        <el-form-item label="系统类型" prop="systemType">
          <el-select v-model="form.systemType" placeholder="请选择系统类型">
            <el-option
              v-for="dict in dict.type.sys_app_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="0">未发布</el-radio>
            <el-radio :label="1">已发布</el-radio>
            <el-radio :label="2">已停用</el-radio>
          </el-radio-group>
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
import { listAppVersion, getAppVersion, delAppVersion, addAppVersion, updateAppVersion } from "@/api/xmbj/appVersion"

export default {
  name: "AppVersion",
  dicts: ['sys_app_version', 'sys_app_type', 'sys_app_status'],
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
      // App版本管理表格数据
      appVersionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        versionName: null,
        versionType: null,
        versionCode: null,
        updateDesc: null,
        updateDescFile: null,
        packageUrl: null,
        systemType: null,
        status: null,
        createdTime: null,
        operator: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        versionName: [
          { required: true, message: "版本名称不能为空", trigger: "blur" }
        ],
        versionType: [
          { required: true, message: "版本类型不能为空", trigger: "change" }
        ],
        versionCode: [
          { required: true, message: "版本号不能为空", trigger: "blur" }
        ],
        packageUrl: [
          { required: true, message: "上传文件包不能为空", trigger: "blur" }
        ],
        systemType: [
          { required: true, message: "系统类型不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询App版本管理列表 */
    getList() {
      this.loading = true
      listAppVersion(this.queryParams).then(response => {
        this.appVersionList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        versionName: null,
        versionType: null,
        versionCode: null,
        updateDesc: null,
        updateDescFile: null,
        packageUrl: null,
        systemType: null,
        status: 0,
        createdTime: null,
        operator: null,
        delFlag: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加App版本管理"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getAppVersion(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改App版本管理"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAppVersion(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addAppVersion(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除App版本管理编号为"' + ids + '"的数据项？').then(function() {
        return delAppVersion(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('xmbj/appVersion/export', {
        ...this.queryParams
      }, `appVersion_${new Date().getTime()}.xlsx`)
    },
    /** 下载文件操作 */
    handleDownload(filePath) {
      if (!filePath) {
        this.$modal.msgWarning("文件路径为空，无法下载")
        return
      }
      window.open(process.env.VUE_APP_BASE_API + filePath, '_blank')
    }
  }
}
</script>
