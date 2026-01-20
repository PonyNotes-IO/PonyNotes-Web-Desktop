<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="笔记标题" prop="title">
        <el-input v-model="form.title" placeholder="请输入笔记标题" />
      </el-form-item>
      <el-form-item label="笔记内容" prop="content">
        <el-input
          v-model="form.content"
          type="textarea"
          placeholder="请输入笔记内容"
          :rows="10"
        />
      </el-form-item>
      <el-form-item label="分类ID" prop="categoryId">
        <el-input-number v-model="form.categoryId" :min="0" placeholder="请输入分类ID" />
      </el-form-item>
      <el-form-item label="标签" prop="tags">
        <el-input v-model="form.tags" placeholder="请输入标签（逗号分隔）" />
      </el-form-item>
      <el-form-item label="是否公开" prop="isPublic">
        <el-radio-group v-model="form.isPublic">
          <el-radio label="0">私有</el-radio>
          <el-radio label="1">公开</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="form.status">
          <el-radio label="0">正常</el-radio>
          <el-radio label="1">停用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getNotes, updateNotes } from '@/api/system/notes'

export default {
  name: 'NotesEdit',
  data() {
    return {
      // 表单参数
      form: {
        id: undefined,
        title: undefined,
        content: undefined,
        categoryId: 0,
        tags: undefined,
        isPublic: 0,
        status: '0',
        remark: undefined
      },
      // 表单校验
      rules: {
        title: [
          { required: true, message: '笔记标题不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '标题长度不能超过100个字符', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '笔记内容不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    const id = this.$route.params.id
    this.getNotesDetail(id)
  },
  methods: {
    /** 获取笔记详情 */
    getNotesDetail(id) {
      getNotes(id).then(response => {
        this.form = response.data
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          updateNotes(this.form).then(response => {
            this.$message.success('修改成功')
            this.$router.push('/system/notes')
          })
        }
      })
    },
    /** 重置按钮 */
    resetForm() {
      this.$refs.form.resetFields()
    }
  }
}
</script>
