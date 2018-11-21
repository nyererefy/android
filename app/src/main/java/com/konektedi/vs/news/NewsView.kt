package com.konektedi.vs.news

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.konektedi.vs.R
import com.konektedi.vs.student.grabPreference
import com.konektedi.vs.utilities.common.Constants.ID
import com.konektedi.vs.utilities.common.Constants.UNIVERSITY
import kotlinx.android.synthetic.main.activity_news_view.*
import kotlinx.android.synthetic.main.news_view_content.*
import java.util.HashMap

class NewsView : AppCompatActivity() {
//    private lateinit var viewModel: CommentsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_view)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        getPost()
    }

    private fun getPost() {
        val data = intent.extras!!

        val post = data.getString("post")
        val title = data.getString("title")
        val postId = data.getInt("post_id")

        post_view.text = title
        title_view.text = post

        getComments(postId)

        initCommenting(postId)
    }

    private fun initCommenting(postId: Int) {
        commentSubmitBtn.setOnClickListener { addComment(postId) }
        commentInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                val comment = commentInput.text.toString()
                if (!comment.isEmpty()) {
                    commentSubmitBtn.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(editable: Editable) {
                val comment = commentInput.text.toString()
                if (comment.isEmpty()) {
                    commentSubmitBtn.visibility = View.GONE
                }
            }
        }
        )
    }

    private fun getComments(post_id: Int) {
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        viewModel = ViewModelProviders.of(this).get(CommentsViewModel::class.java)
//
//        val adapter = CommentsAdapter(this)
//
//        viewModel.getPostList(post_id).observe(this, Observer { adapter.submitList(it) })
//
//        viewModel.networkState.observe(this, Observer { adapter.setNetworkState(it) })
//
//        recyclerView.addItemDecoration(DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL))
//        recyclerView.adapter = adapter
    }

    private fun addComment(post_id: Int) {
        showProgressBar(true)
        val comment = commentInput.text.toString()

        val map = HashMap<String, String>()

        map["id"] = grabPreference(this, ID)
        map["comment"] = comment
        map["post_id"] = post_id.toString()
        map["university_id"] = grabPreference(this, UNIVERSITY)

        hideKeyboard()
        commentInput.setText("")

//        val call = ApiUtilities.getClient().postComment(map)
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                showProgressBar(false)
//
//                if (response.isSuccessful) {
//                    hideKeyboard()
//                    Toast.makeText(this@NewsView, "success", Toast.LENGTH_SHORT).show()
//                    getComments(post_id)
//
//                } else {
//                    hideKeyboard()
//                    commentInput.setText(comment)
//                    Toast.makeText(this@NewsView, R.string.error_occurred, Toast.LENGTH_SHORT).show()
//                }
//
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                showProgressBar(false)
//                Toast.makeText(this@NewsView, R.string.error_occurred, Toast.LENGTH_SHORT).show()
//            }
//        })
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun onRetryClick(view: View, position: Int) {

    }

    private fun showProgressBar(boolean: Boolean) =
            when {
                boolean -> progress_bar.visibility = View.VISIBLE
                else -> progress_bar.visibility = View.GONE
            }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return true
    }

}
