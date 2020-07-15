package com.example.kakaologin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.callback.UnLinkResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var callback: SessionCallback = SessionCallback()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 로그아웃
        logout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("로그아웃 하시겠습니까?")
            builder.setPositiveButton("확인") { dialogInterface, i ->
                UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
                    override fun onCompleteLogout() {

                    }
                })
                dialogInterface.dismiss()
            }
            builder.setNegativeButton("취소") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        // 앱연결 해제
        link_out.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("앱 연결을 해제하시겠습니까?")
            builder.setPositiveButton("확인"){ dialogInterface, i ->
                UserManagement.getInstance().requestUnlink(object : UnLinkResponseCallback() {
                    override fun onFailure(errorResult: ErrorResult?) {
                        Log.i("Log",errorResult!!.toString())
                    }
                    override fun onSessionClosed(errorResult: ErrorResult) {
                    }
                    override fun onNotSignedUp() {
                    }
                    override fun onSuccess(userId: Long?) {
                        Log.i("Log",userId.toString())
                    }
                })
                dialogInterface.dismiss()
            }
            builder.setNegativeButton("취소") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        Session.getCurrentSession().addCallback(callback);



        Log.i("clear", "${MeV2Response.KEY_NICKNAME}")


    }

    @SuppressLint("MissingSuperCall")
    override fun onDestroy() {
        Session.getCurrentSession().removeCallback(callback);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            Log.i("Log", "session get current session")
            return
        }
        super.onActivityResult(requestCode, resultCode, data)


    }
}
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // 키해시
//        var hash_key = getKeyHash(this)
//        Log.i("Log","${hash_key}") // 확인
//    }
//
//    // 키해시 구하기
//    fun getKeyHash(context: Context): String? {
//        val packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES) ?: return null
//
//        for (signature in packageInfo!!.signatures) {
//            try {
//                val md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//
//                return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
//            } catch (e: NoSuchAlgorithmException) {
//                Log.w("Log", "Unable to get MessageDigest. signature=$signature", e)
//            }
//
//        }
//        return null
//    }
//}