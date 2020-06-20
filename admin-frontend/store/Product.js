export const actions = {
  getProducts({
    commit
  }, params) {
    return this.$api.product.getProducts(params).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg);
      }
    }).catch(error => {
      return Promise.reject(error);
    })
  },
  addProduct({
    commit
  }, params) {
    return this.$api.product.addProduct(params).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg);
      }
    }).catch(error => {
      return Promise.reject(error);
    })
  },
  updateProduct({
    commit
  }, id, params) {
    return this.$api.product.addProduct(params).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg);
      }
    }).catch(error => {
      return Promise.reject(error);
    })
  },
  getProductByID({
    commit
  }, id, params) {
    return this.$api.product.getProductByID(params).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg);
      }
    }).catch(error => {
      return Promise.reject(error);
    })
  },
  getCategories({
    commit
  }, params) {
    return this.$api.product.getCategories(params).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg);
      }
    }).catch(error => {
      return Promise.reject(error);
    })
  },
  updateCategory({
    commit
  }, params) {
    return this.$api.product.updateCategory(params).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg);
      }
    }).catch(error => {
      return Promise.reject(error);
    })
  },
  getCategoryByID({
    commit
  }, id) {
    return this.$api.product.getCategoryByID(id).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg);
      }
    }).catch(error => {
      return Promise.reject(error);
    })
  },
  addCategory({
    commit
  }, params) {
    return this.$api.product.addCategory(params).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg);
      }
    }).catch(error => {
      return Promise.reject(error);
    })
  },
  deleteCategory({commit}, id){
    return this.$api.product.deleteCategory(id).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg);
      }
    }).catch(error => {
      return Promise.reject(error);
    })
  },
  getAttrs({
    commit
  }, params) {
    return this.$api.product.getAttrs(params).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg);
      }
    }).catch(error => {
      return Promise.reject(error);
    })
  }
}
