// Copyright (C) 2013 Andrea Jonus
// See the LICENSE file for the full license notice
package org.homelinux.tapiri.jei.essem;


import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;

public class AutoFitTextView extends EditText {

public AutoFitTextView(Context context) {
    super(context);
    init();
}

public AutoFitTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
}

private void init() {

    maxTextSize = this.getTextSize();
    if (maxTextSize < 35) {
        maxTextSize = 30;
    }
    minTextSize = 20;
    previousChars = 0;
}

private void refitText(String text, int textWidth, int textHeight) {
	if (textWidth > 0) {
		// Subtract a little extra space to account for line height
        int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
        int availableHeight = textHeight - this.getPaddingTop() - this.getPaddingBottom() - 20;
        String largestLine = getLargestLine(text);
        float trySize = maxTextSize;

        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
        while ((trySize > minTextSize)
                && ( (this.getPaint().measureText(largestLine) > availableWidth)
                || ((this.getLineHeight() * getOriginalLineCount()) > availableHeight) )) {
            trySize -= 1;
            if (trySize <= minTextSize) {
                trySize = minTextSize;
                break;
            }
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
        }
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
    }
}

private void refitTextOnChanged(String text, int textWidth, int textHeight) {
	if (textWidth > 0) {
		// Subtract a little extra space to account for line height
        int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
        int availableHeight = textHeight - this.getPaddingTop() - this.getPaddingBottom() - 20;
        String largestLine = getLargestLine(text);
        int currentChars = text.length();
        
		if (currentChars != previousChars) {
			float trySize = this.getTextSize();
			
			if (currentChars > previousChars) {
				while ((trySize > minTextSize)
				&& ( (this.getPaint().measureText(largestLine) > availableWidth)
				|| ((this.getLineHeight() * getOriginalLineCount()) > availableHeight) )) {
					trySize -= 1;
					if (trySize <= minTextSize) {
						trySize = minTextSize;
						this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
						break;
					}
					this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
				}
			} else {
				while ((trySize < maxTextSize)
				&& (this.getPaint().measureText(largestLine) < availableWidth)
				&& ((this.getLineHeight() * getOriginalLineCount()) < availableHeight) ) {
					trySize += 1;
					if (trySize >= maxTextSize) {
						trySize = maxTextSize;
						this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
						break;
					}
					this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
				}
				// It's possible that the size of the text is now bigger than the available space
				if ((this.getPaint().measureText(largestLine) > availableWidth)
				|| ((this.getLineHeight() * getOriginalLineCount()) > availableHeight)) {
					trySize-=1;
					this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
				}
			}
			previousChars = currentChars;
		} else {
	        float trySize = maxTextSize;

	        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
	        while ((trySize > minTextSize)
	                && ( (this.getPaint().measureText(largestLine) > availableWidth)
	                || ((this.getLineHeight() * getOriginalLineCount()) > availableHeight) )) {
	            trySize -= 1;
	            if (trySize <= minTextSize) {
	                trySize = minTextSize;
	                break;
	            }
	            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
	        }
	        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
		}
    }
}

// EditText automatically wraps text, so we get the line count from the original input
private int getOriginalLineCount() {
	String fullText = this.getText().toString();
	String eol = System.getProperty("line.separator");
	
	int count = StringUtils.countMatches(fullText, eol) + 1;
	
	return count;
}

// Get the line which has the largest area
private String getLargestLine(String fullText) {
	String largestLine = "";
	String eol = System.getProperty("line.separator");
	String[] lines = fullText.split(eol);
	
	for (int i = 0; i < lines.length; i++) {
		if (this.getPaint().measureText(lines[i]) > this.getPaint().measureText(largestLine)) {
			largestLine = lines[i];
		}
	}
	
	return largestLine;
	
}

@Override
protected void onTextChanged(final CharSequence text, final int start,
        final int before, final int after) {
    refitTextOnChanged(this.getText().toString(), this.getWidth(), this.getHeight());
}

@Override
protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    if ((w != oldw) || (h != oldh)) {
        refitText(this.getText().toString(), this.getWidth(), this.getHeight());
    }
}

@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
    //int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
    //refitText(this.getText().toString(), parentWidth, parentHeight);
}

public float getMinTextSize() {
    return minTextSize;
}

public void setMinTextSize(int minTextSize) {
    this.minTextSize = minTextSize;
}

public float getMaxTextSize() {
    return maxTextSize;
}

public void setMaxTextSize(int minTextSize) {
    this.maxTextSize = minTextSize;
}

private float minTextSize;
private float maxTextSize;
private int previousChars;

}
