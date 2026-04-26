package com.example.chatapptest.ui.chatpage

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapptest.R
import com.example.chatapptest.database.model.RommData
import com.example.chatapptest.databinding.ActivityChatBinding
import com.example.chatapptest.ui.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var adapter: MessageAdapter
    private val typingDotAnimators = mutableListOf<ObjectAnimator>()
    private var hasAutoScrolled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        adapter = MessageAdapter { viewModel.currentUserId }
        initview()
        setupInsets()
        setupRecyclerView()
        setupInteractions()
        initparams()
        subscribeToLiveData()
        startTypingIndicatorAnimation()
    }

    private fun initview() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView() {
        binding.messagerecyler.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity).apply {
                stackFromEnd = true
            }
            adapter = this@ChatActivity.adapter
            setHasFixedSize(false)
        }
    }

    private fun setupInteractions() {
        binding.chatBackButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.inputtextmessage.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                viewModel.sendmessage()
                true
            } else {
                false
            }
        }
    }

    private fun initparams() {
        val room = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constant.EXTRA_ROOM, RommData::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(Constant.EXTRA_ROOM) as RommData?
        }
        viewModel.changeroom(room)
    }

    private fun subscribeToLiveData() {
        viewModel.ToastLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.messagesLiveData.observe(this) { messages ->
            adapter.submitMessages(messages) {
                if (messages.isEmpty()) return@submitMessages

                binding.messagerecyler.post {
                    val lastIndex = adapter.itemCount - 1
                    if (lastIndex < 0) return@post

                    if (hasAutoScrolled) {
                        binding.messagerecyler.smoothScrollToPosition(lastIndex)
                    } else {
                        binding.messagerecyler.scrollToPosition(lastIndex)
                        hasAutoScrolled = true
                    }
                }
            }
        }
    }

    private fun startTypingIndicatorAnimation() {
        val dots = listOf(binding.typingDotOne, binding.typingDotTwo, binding.typingDotThree)
        dots.forEachIndexed { index, dotView ->
            dotView.alpha = 0.35f
            val animator = ObjectAnimator.ofFloat(dotView, View.ALPHA, 0.35f, 1f, 0.35f).apply {
                duration = 900L
                startDelay = index * 140L
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.RESTART
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }
            typingDotAnimators += animator
        }
    }

    override fun onDestroy() {
        typingDotAnimators.forEach { it.cancel() }
        typingDotAnimators.clear()
        super.onDestroy()
    }
}
