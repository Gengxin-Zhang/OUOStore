export const state = () => ({
  info: {
    id: "",
    permission: -1,
    name: "",
    user_id: "",
  },
  signed: false,
  token: ""
})

export const mutations = {
  setUserInfo(state, user) {
    state.info = user;
    state.signed = true;
  },
  setToken(state, token) {
    state.token = token
    localStorage.setItem("token", token)
  },
  removeUserInfo(state) {
    state.info = {
      id: "",
      permission: -1,
      name: "",
      user_id: "",
    };
    state.signed = false;
  }
}

export const actions = {
  signin({
    commit
  }, credentials) {
    if (credentials.username == "" || credentials.password == "") {
      return Promise.reject("请填写完整登入信息");
    }
    return this.$api.auth.signin(credentials).then(res => {
      if (res.data.status) {
        // commit("setUserInfo", res.data.data.user);
        commit("setToken", res.data.data.access_token)
        return Promise.resolve();
      } else {
        return Promise.reject(res.data.msg);
      }
    }, err => {
      // return Promise.resolve();
      return Promise.reject(err);
    })
  },
  signout({
    commit
  }) {
    commit("removeUserInfo");
  },

  validToken({
    commit
  }, token) {
    if (token === null || token === undefined || token === "") {
      return Promise.reject("token无效")
    }
    commit("setToken", token)
    return this.$api.auth.validToken(token).then(res => {
      if (res.data.status) {
        commit("setUserInfo", res.data.data.user);
        return Promise.resolve();
      } else {
        return Promise.reject(res.data.msg)
      }
    }, err => {
      return Promise.reject(err);
    })
  },
  getUsers({
    commit
  }, params) {
    return this.$api.user.getUsers(params).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg)
      }
    }).catch(err => {
      return Promise.reject(err)
    })
  },
  deleteUser({
    commit
  }, id) {
    return this.$api.user.deleteUserById(id).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg)
      }
    }).catch(err => {
      return Promise.reject(err)
    })
  },
  getRoles({
    commit
  }, params) {
    return this.$api.user.getRoles(params).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg)
      }
    }).catch(err => {
      return Promise.reject(err)
    })
  },
  deleteRole({
    commit
  }, id) {
    return this.$api.user.deleteRoleById(id).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg)
      }
    }).catch(err => {
      return Promise.reject(err)
    })
  },
  updateRole({
    commit
  }, params) {
    console.log(params)
    return this.$api.user.updateRoleById(params.id, params).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg)
      }
    }).catch(err => {
      return Promise.reject(err)
    })
  },
  addRole({
    commit
  }, params) {
    return this.$api.user.addRole(params).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg)
      }
    }).catch(err => {
      return Promise.reject(err)
    })
  },
  getMenus({
    commit
  }, params) {
    return this.$api.user.getMenus(params).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg)
      }
    }).catch(err => {
      return Promise.reject(err)
    })
  },
  deleteMenuById({
    commit
  }, id) {
    return this.$api.user.deleteMenuById(id).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg)
      }
    }).catch(err => {
      return Promise.reject(err)
    })
  },
  updateMenu({
    commit
  }, params) {
    return this.$api.user.updateMenuById(params).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg)
      }
    }).catch(err => {
      return Promise.reject(err)
    })
  },
  addMenu({
    commit
  }, params) {
    return this.$api.user.addMenu(params).then(res => {
      if (res.data.status) {
        return Promise.resolve(res.data.data);
      } else {
        return Promise.reject(res.data.msg)
      }
    }).catch(err => {
      return Promise.reject(err)
    })
  }
}
