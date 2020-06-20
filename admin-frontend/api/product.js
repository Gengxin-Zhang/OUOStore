export default ($axios) => ({
  getProducts(params) {
    return $axios.get("/products", params)
  },
  addProduct(params) {
    return $axios.post("/products", params)
  },
  getProductByID(id){
    return $axios.get(`/products/${id}`)
  },
  updateProduct(id, params){
    return $axios.put(`/products/${id}`, params)
  },
  getAttrs(params){
    return $axios.get("/")
  },
  getCategories(params){
    return $axios.get("/product_categories", params)
  },
  updateCategory(params){
    return $axios.put(`product_categories/${params.id}`, params)
  },
  getCategoryByID(id){
    return $axios.get(`product_categories/${id}`)
  },
  addCategory(params){
    return $axios.post(`product_categories`, params)
  },
  deleteCategory(id){
    return $axios.delete(`product_categories/${id}`)
  },
  
});
