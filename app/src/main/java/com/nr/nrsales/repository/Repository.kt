package com.nr.nrsales.repository

import com.nr.nrsales.apis.RemoteDataSource
import com.nr.nrsales.model.AddFundRes
import com.nr.nrsales.model.BaseApiResponse
import com.nr.nrsales.model.BasicRes
import com.nr.nrsales.model.NotificationRes
import com.nr.nrsales.model.OutFundRes
import com.nr.nrsales.model.OutFundResAdmin
import com.nr.nrsales.model.PositionRes
import com.nr.nrsales.model.RegisterResModel
import com.nr.nrsales.model.UserDashboardModel
import com.nr.nrsales.model.UserListRes
import com.nr.nrsales.model.UserRes
import com.nr.nrsales.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody
import javax.inject.Inject


@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    suspend fun getLogin(Map: HashMap<String, Any>): Flow<NetworkResult<UserRes>> {
        return flow {
            emit(safeApiCall { remoteDataSource.loginUser(Map) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRegistered(body: RequestBody): Flow<NetworkResult<RegisterResModel>> {
        return flow {
            emit(safeApiCall { remoteDataSource.registerUser(body) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun updateUser(body: RequestBody): Flow<NetworkResult<RegisterResModel>> {
        return flow {
            emit(safeApiCall { remoteDataSource.updateUser(body) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addFunds(body: RequestBody): Flow<NetworkResult<BasicRes>> {
        return flow {
            emit(safeApiCall { remoteDataSource.addFunds(body) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun outFunds(body: RequestBody): Flow<NetworkResult<BasicRes>> {
        return flow {
            emit(safeApiCall { remoteDataSource.outFunds(body) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun get_profile(Map: HashMap<String, Any>): Flow<NetworkResult<RegisterResModel>> {
        return flow {
            emit(safeApiCall { remoteDataSource.get_profile(Map) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFund(Map: HashMap<String, Any>): Flow<NetworkResult<AddFundRes>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getFund(Map) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun out_funds_list(Map: HashMap<String, Any>): Flow<NetworkResult<OutFundRes>> {
        return flow {
            emit(safeApiCall { remoteDataSource.out_funds_list(Map) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun get_out_funds_list_admin(Map: HashMap<String, Any>): Flow<NetworkResult<OutFundResAdmin>> {
        return flow {
            emit(safeApiCall { remoteDataSource.get_out_funds_list_admin(Map) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun user_dashboard(Map: HashMap<String, Any>): Flow<NetworkResult<UserDashboardModel>> {
        return flow {
            emit(safeApiCall { remoteDataSource.user_dashboard(Map) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun get_all_user(Map: HashMap<String, Any>): Flow<NetworkResult<UserListRes>> {
        return flow {
            emit(safeApiCall { remoteDataSource.get_all_user(Map) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun get_add_funds_list_admin(Map: HashMap<String, Any>): Flow<NetworkResult<AddFundRes>> {
        return flow {
            emit(safeApiCall { remoteDataSource.get_add_funds_list_admin(Map) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun out_funt_accept_reject(Map: HashMap<String, Any>): Flow<NetworkResult<BasicRes>> {
        return flow {
            emit(safeApiCall { remoteDataSource.out_funt_accept_reject(Map) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun position(Map: HashMap<String, Any>): Flow<NetworkResult<BasicRes>> {
        return flow {
            emit(safeApiCall { remoteDataSource.position(Map) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun update_user_status(Map: HashMap<String, Any>): Flow<NetworkResult<BasicRes>> {
        return flow {
            emit(safeApiCall { remoteDataSource.update_user_status(Map) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun add_fund_accept_reject(Map: HashMap<String, Any>): Flow<NetworkResult<BasicRes>> {
        return flow {
            emit(safeApiCall { remoteDataSource.add_fund_accept_reject(Map) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun get_notification(Map: HashMap<String, Any>): Flow<NetworkResult<NotificationRes>> {
        return flow {
            emit(safeApiCall { remoteDataSource.get_notification(Map) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun position_list(Map: HashMap<String, Any>): Flow<NetworkResult<PositionRes>> {
        return flow {
            emit(safeApiCall { remoteDataSource.position_list(Map) })
        }.flowOn(Dispatchers.IO)
    }
}
