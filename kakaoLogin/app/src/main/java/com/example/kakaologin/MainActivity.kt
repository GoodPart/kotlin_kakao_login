package com.example.kakaologin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kakao.auth.Session


class MainActivity : AppCompatActivity() {

    private var callback: SessionCallback = SessionCallback()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Session.getCurrentSession().addCallback(callback);

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