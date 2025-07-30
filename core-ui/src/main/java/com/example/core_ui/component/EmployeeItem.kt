package com.example.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.core_ui.theme.Dimensions
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp


@Composable
fun EmployeeItem(
    modifier: Modifier = Modifier, // Changed default to Modifier for better reusability
    profileImageUrl: String?,
    employeeName: String,
    companyName: String,
) {
    Row(
        modifier = modifier//.fillMaxWidth().height(100.dp).background(Color.Blue) // Use the passed modifier, which should include fillMaxWidth from caller
            .padding(all = Dimensions.dimen_16), // Overall padding for the item
        verticalAlignment = Alignment.CenterVertically // Align avatar and text column vertically centered
    ) {
        // Avatar Section
        if (profileImageUrl != null) {
            CircularProfileImage(
                modifier =Modifier,
                imageSource = profileImageUrl,
                size = Dimensions.dimen_48
            )
        } else {
            Box(
                modifier = Modifier
                    .size(Dimensions.dimen_48)
                    .clip(CircleShape)
                    .background(color = Color.Green),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = employeeName.firstOrNull()?.toString()?.uppercase() ?: "?",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.width(Dimensions.dimen_16))
        // Text Details Section
        Column(
            modifier = Modifier.weight(1f) // Allow this column to take available width
        ) {
            Text(
                text = employeeName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis // Good practice
            )
            Text(
                text = companyName,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp, // Kept same as original, consider reducing if too large
                maxLines = 1,
                overflow = TextOverflow.Ellipsis, // Good practice
                modifier = Modifier.padding(top = Dimensions.dimen_8 / 2) // Small space between name and company
            )
        }
    }
}

/*


@Composable
fun EmployeeItem(
    modifier: Modifier,
    profileImageUrl: String?,
    employeeName: String,
    companyName: String,
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (avatar, employeeNameRef, company) = createRefs()

        profileImageUrl?.let {
            CircularProfileImage(
                modifier =
                    Modifier.constrainAs(avatar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                imageSource = profileImageUrl,
                size = Dimensions.dimen_32
            )
        } ?: Box(
            modifier = Modifier
                .size(Dimensions.dimen_32)
                .clip(CircleShape)
                .background(color = Color.Green),
            contentAlignment = Alignment.Center
        ) {
            Text(text = employeeName)
        }

        Text(
            text = employeeName,
            modifier =
                Modifier.constrainAs(employeeNameRef) {
                    top.linkTo(avatar.top)
                    start.linkTo(avatar.end, margin = Dimensions.dimen_16)
                    end.linkTo(parent.end, margin = Dimensions.dimen_8)
                    width = Dimension.fillToConstraints
                },
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            maxLines = 1
        )

        Text(
            text = companyName,
            modifier =
                Modifier.constrainAs(company) {
                    top.linkTo(employeeNameRef.bottom)
                    start.linkTo(employeeNameRef.start)
                    end.linkTo(parent.end, margin = Dimensions.dimen_8)
                    width = Dimension.fillToConstraints
                },
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            maxLines = 1
        )
    }
}
*/
