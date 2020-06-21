export const actions = {
    getSystemInfo({
      commit
    }) {
      return this.$api.util.getSystemInfo().then(res => {
        if (res.data.status) {
          return Promise.resolve(res.data.data);
        } else {
          return Promise.reject(res.data.msg);
        }
      }).catch(error => {
        return Promise.reject(error);
      })
    },
}