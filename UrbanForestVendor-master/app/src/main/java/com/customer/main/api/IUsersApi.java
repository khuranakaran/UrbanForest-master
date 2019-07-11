package com.customer.main.api;

import com.customer.main.model.request.add_product_request.AddProductRequest;
import com.customer.main.model.request.add_to_cart.AddCart;
import com.customer.main.model.request.cancel_order.CancelOrder;
import com.customer.main.model.request.change_password_request.ChangePasswordRequest;
import com.customer.main.model.request.changer_order_request.ChangeOrderStatusRequest;
import com.customer.main.model.request.delete_cart.DeleteCart;
import com.customer.main.model.request.delete_my_product_request.DeleteMyProductRequest;
import com.customer.main.model.request.forgot_password_request.ForgotPasswordRequest;
import com.customer.main.model.request.get_all_products.AllProducts;
import com.customer.main.model.request.get_my_orders.GetMyOrders;
import com.customer.main.model.request.get_product_details_by_id.ProductIdRequest;
import com.customer.main.model.request.login_request.LoginRequest;
import com.customer.main.model.request.order_request.OrderRequest;
import com.customer.main.model.request.orders_request.OrdersRequest;
import com.customer.main.model.request.place_order.PlaceOrder;
import com.customer.main.model.request.profile_request.ProfileRequest;
import com.customer.main.model.request.profile_update_request.ProfileUpdateRequest;
import com.customer.main.model.request.signup_request.SignupRequest;
import com.customer.main.model.request.update_cart.UpdateCart;
import com.customer.main.model.request.update_my_product_request.UpdateMyProductRequest;
import com.customer.main.model.response.OrderDetailsResponse.OrderDetailsResponse;
import com.customer.main.model.response.VendorOrdersResponse.VendorOrdersResponse;
import com.customer.main.model.response.categories_response.CategoriesResponse;
import com.customer.main.model.response.customer_my_orders.MyOrdersResponse;
import com.customer.main.model.response.general_response.GeneralResponse;
import com.customer.main.model.response.my_cart_response.MyCartResponse;
import com.customer.main.model.response.my_products_request.MyProductsRequest;
import com.customer.main.model.response.my_products_response.MyProductsResponse;
import com.customer.main.model.response.order_response.OrderResponse;
import com.customer.main.model.response.product_details_response.ProductDetails;
import com.customer.main.model.response.profile_response.ProfileResponse;
import com.customer.main.model.response.user_response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 *
 * @author Karan
 * @date 1/2/19
 */

public interface IUsersApi {

    /**
     * @param signupRequest
     * @return
     */
    @POST("register.php")
    Call<UserResponse> signUp(@Body SignupRequest signupRequest);

    /**
     * @param loginRequest
     * @return
     */
    @POST("login.php")
    Call<UserResponse> login(@Body LoginRequest loginRequest);

    /**
     * @param profileRequest
     * @return
     */
    @POST("profile.php")
    Call<ProfileResponse> profile(@Body ProfileRequest profileRequest);


    /**
     * @param profileUpdateRequest
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("updateprofile.php")
    Call<GeneralResponse> updateProfile(@Body ProfileUpdateRequest profileUpdateRequest);


    /**
     * @param orderRequest
     * @return
     */
    @POST("orders.php")
    Call<OrderResponse> orders(@Body OrderRequest orderRequest);


    /**
     * @param orderRequest
     * @return
     */
    @POST("vedorOrders.php")
    Call<VendorOrdersResponse> vendorOrders(@Body OrdersRequest orderRequest);


    /**
     * @param orderRequest
     * @return
     */
    @POST("acceptedOrders.php")
    Call<VendorOrdersResponse> vendorMyOrders(@Body OrdersRequest orderRequest);

    /**
     * @param orderRequest
     * @return
     */
    @POST("vendorOrderDetails.php")
    Call<OrderDetailsResponse> orderDetails(@Body OrdersRequest orderRequest);

    @GET("allProducts.php")
    Call<AllProducts> getAllProducts();

    /**
     *
     * @param productIdRequest
     * @return
     */
    @POST("getProductbyId.php")
    Call<ProductDetails> getProductbyId(@Body ProductIdRequest productIdRequest);

    /**
     * @param addProductRequest
     * @return
     */
    @POST("addProduct.php")
    Call<GeneralResponse> addProduct(@Body AddProductRequest addProductRequest);

    /**
     * @param changePasswordRequest
     * @return
     */
    @POST("changepwd.php")
    Call<GeneralResponse> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    /**
     * @param forgotPasswordRequest
     * @return
     */
    @POST("forgotpwd.php")
    Call<GeneralResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    /**
     * @param myProductsRequest
     * @return
     */
    @POST("myProducts.php")
    Call<MyProductsResponse> myProducts(@Body MyProductsRequest myProductsRequest);

    /**
     * @param changeOrderStatusRequest
     * @return
     */
    @POST("changeorderstatus.php")
    Call<GeneralResponse> changeOrderStatus(@Body ChangeOrderStatusRequest changeOrderStatusRequest);

    /**
     * @param updateMyProductRequest
     * @return
     */
    @POST("updateProduct.php")
    Call<GeneralResponse> updateMyProduct(@Body UpdateMyProductRequest updateMyProductRequest);

    /**
     * @param deleteMyProductRequest
     * @return
     */
    @POST("deleteProduct.php")
    Call<GeneralResponse> deleteMyProduct(@Body DeleteMyProductRequest deleteMyProductRequest);





    /**
     * @param deleteCart
     * @return
     */
    @POST("deleteCart.php")
    Call<GeneralResponse> deleteCart(@Body DeleteCart deleteCart);

    /**
     * @param getMyOrders
     * @return
     */
    @POST("getMyOrders.php")
    Call<MyOrdersResponse> getMyOrders(@Body GetMyOrders getMyOrders);

    /**
     * @param cancelOrder
     * @return
     */
    @POST("cancelOrder.php")
    Call<GeneralResponse> cancelOrder(@Body CancelOrder cancelOrder);

    /**
     * @param placeOrder
     * @return
     */
    @POST("placeOrder.php")
    Call<GeneralResponse> placeOrder(@Body PlaceOrder placeOrder);
    /**
     * @param updateCart
     * @return
     */
    @POST("updateCart.php")
    Call<GeneralResponse> updateCart(@Body UpdateCart updateCart);

    /**
     * @param addCart
     * @return
     */
    @POST("addCart.php")
    Call<GeneralResponse> addCart(@Body AddCart addCart);

    @POST("getCart.php")
    Call<MyCartResponse> getCart(@Body GetMyOrders getMyOrders);

    @GET("getCategories.php")
    Call<CategoriesResponse> getCategories();


}
