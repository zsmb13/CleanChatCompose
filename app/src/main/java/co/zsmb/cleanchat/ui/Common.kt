package co.zsmb.cleanchat.ui

import android.text.format.DateUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.zsmb.cleanchat.R
import co.zsmb.cleanchat.data.User
import co.zsmb.cleanchat.data.randomUserList
import co.zsmb.cleanchat.ui.theme.AppColors
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun Avatar(
    url: String,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = rememberCoilPainter(url),
        contentDescription = null,
        modifier = modifier
            .size(64.dp)
            .clip(RoundedCornerShape(12.dp))
    )
}

@Composable
fun UserRow(user: User, modifier: Modifier = Modifier) {
    @Composable
    fun OnlineIndicator() {
        Box(
            Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(AppColors.onlineIndicator)
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(24.dp),
    ) {
        Avatar(url = user.avatarUrl, modifier = Modifier.padding(end = 16.dp))

        Column(modifier = Modifier.weight(1f, fill = true)) {
            Text(
                user.name,
                fontWeight = FontWeight.Bold,
                color = AppColors.textDark,
            )
            Text(
                lastActiveStatus(user),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 4.dp),
                color = AppColors.textMedium,
                fontSize = 14.sp,
            )
        }

        if (user.isOnline) {
            OnlineIndicator()
        }
    }
}

@Composable
@ReadOnlyComposable
private fun lastActiveStatus(user: User): String {
    return when {
        user.isOnline -> stringResource(R.string.user_online)
        else -> stringResource(
            R.string.user_last_activity,
            DateUtils.getRelativeTimeSpanString(user.lastOnline.time)
        )
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun UserRowPreview() {
    Column {
        randomUserList(5).forEach { user ->
            UserRow(user = user)
        }
    }
}
