package com.albert.feature_recipes.presentation.ui.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.albert.feature_recipes.domain.CommentModel
import com.albert.feature_recipes.presentation.R


@Composable
fun CommentList(comments: List<CommentModel>, onSendComment: (commentText: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = "Comentarios",
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            color = colorResource(R.color.grey_90),
        )
        LazyColumn(
            modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(comments) { comment ->
                CommentItem(comment)
            }
        }
        AddCommentSection(onSendComment)
    }
}

@Composable
fun CommentItem(comment: CommentModel) {
    Card {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            comment.nameUser?.let {
                Text(text = it, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(4.dp))
            comment.commentText?.let {
                Text(text = it, fontSize = 14.sp, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(4.dp))
            comment.timeRegister?.let {
                Text(text = it, fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                comment.stars?.toInt()?.let {
                    repeat(it) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFFF9A825)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCommentSection(onSendComment: (commentText: String) -> Unit) {
    val commentText = remember { mutableStateOf("") }
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            OutlinedTextField(
                value = commentText.value,
                onValueChange = { commentText.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Comentario") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    onSendComment(commentText.value)
                    commentText.value = ""

                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Enviar")
            }
        }
    }
}