package co.zsmb.cleanchat.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import co.zsmb.cleanchat.R
import co.zsmb.cleanchat.data.Message
import co.zsmb.cleanchat.data.randomMessageList
import co.zsmb.cleanchat.ui.theme.AppColors
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun Messages() {
    val messages = remember { randomMessageList() }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.bgLight),
    ) {
        val listState = rememberLazyListState()
        MessageList(messages, listState)
        Toolbar(messages.size, listState)
    }
}

@Composable
private fun Toolbar(messageCount: Int, listState: LazyListState) {
    val toolbarHeight = with(LocalDensity.current) { 60.dp.toPx() }.toFloat()
    val alpha = if (listState.firstVisibleItemIndex > 0) {
        0f
    } else {
        1 - (listState.firstVisibleItemScrollOffset / toolbarHeight)
    }.coerceAtLeast(0f)

    Row(
        modifier = Modifier
            .alpha(alpha)
            .background(AppColors.bgLight)
            .padding(top = 16.dp)
            .padding(vertical = 16.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            stringResource(R.string.messages_title),
            modifier = Modifier.weight(1f, fill = true),
            color = AppColors.textDark,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            stringResource(R.string.messages_new_count, messageCount),
            modifier = Modifier.padding(top = 4.dp),
            color = AppColors.textMedium,
            fontSize = 14.sp,
        )
    }
}

@Composable
private fun MessageList(messages: List<Message>, listState: LazyListState) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp, top = 80.dp)
    ) {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Composable
private fun MessageCard(message: Message) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(vertical = 12.dp),
        elevation = 0.5.dp
    ) {
        Column {
            UserRow(user = message.user)
            MessageBody(
                message,
                modifier = Modifier.padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 16.dp,
                )
            )
            if (message.imageUrl != null) {
                MessageImageAttachment(
                    message,
                    modifier = Modifier
                        .padding(top = 12.dp, start = 24.dp, end = 24.dp, bottom = 24.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }
    }
}

@Composable
private fun MessageImageAttachment(message: Message, modifier: Modifier) {
    Image(
        painter = rememberCoilPainter(message.imageUrl),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
private fun MessageBody(message: Message, modifier: Modifier = Modifier) {
    Text(
        text = message.text,
        color = AppColors.textMedium,
        modifier = modifier,
        lineHeight = 1.7f.em,
        fontWeight = FontWeight.Medium,
    )
}
