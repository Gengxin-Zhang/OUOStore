import Auth from "@/api/auth"
import Product from "@/api/product"
import User from '@/api/user'

export default (context, inject) => {
  // Initialize API repositories
  const repositories = {
    auth: Auth(context.$axios),
    product: Product(context.$axios),
    user: User(context.$axios)
  };
  inject("api", repositories);
};
