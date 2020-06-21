export default ($axios) => ({
  getUsers(params) {
    return $axios.get('/admin/users/search/', params)
  },
  deleteUserById(id) {
    return $axios.delete(`/admin/users/${id}`)
  },
  updateUser(id, params) {
    return $axios.put(`/admin/users/${id}`, params)
  },
  addUser(params) {
    return $axios.post('/admin/users', params)
  },
  getRoles(params) {
    return $axios.get('/roles/', params)
  },
  deleteRoleById(id) {
    return $axios.delete(`/roles/${id}`)
  },
  updateRoleById(id, params) {
    return $axios.put(`/roles/${id}`, params)
  },
  addRole(params) {
    return $axios.post('/roles', params)
  },
  getMenus(params) {
    return $axios.get('/menus', params)
  },
  deleteMenuById(id) {
    return $axios.delete(`/menus/${id}`)
  },
  updateMenuById(id, params) {
    return $axios.put(`/menus/${id}`, params)
  },
  addMenu(params) {
    return $axios.post('/menus', params)
  },
  removeMenu(roleId, menuId) {
    return $axios.delete(`/roles/${roleId}/menus/${menuId}`)
  },
  giveMenu(roleId, menuId) {
    return $axios.post(`/roles/${roleId}/menus/${menuId}`)
  },
  getMenusByRole(roleId) {
    return $axios.get(`/roles/${roleId}/menus`)
  },
  giveRole(userId, roleId) {
    return $axios.post(`/admin/users/${userId}/roles/${roleId}`)
  },
  removeRole(userId, roleId) {
    return $axios.delete(`/admin/users/${userId}/roles/${roleId}`)
  },
  signin(credentials) {
    return $axios.post("/admin/oauth/access_token", credentials)
  },
  validToken(token) {
    return $axios.get("/roles", {
      token
    })
  },

});
