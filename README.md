# Android TextView Click Bug Sample
Sample app to show a possible bug(s) in TextView.class


  Was working with a TextView that had multiple ClickableSpans and had `autoLink="email|phone"` to handle email/phone. When touching on one of the links, it would use a different span to handle the click on occassion. The TextView with id of `wrongClick` would demonstrate that behavior. 

After looking through the code for `onTouch(MotionEvent event)` in the TextView class after running into a click related bug, I noticed that `ClickableSpan[] links = ((Spannable) mText).getSpans(getSelectionStart(),getSelectionEnd(), ClickableSpan.class);` was returning the wrong span.

Setting a `LinkMovementMethod` on the TextView resolved the wrong span clicked issue since it used the `LinkMovementMethod`'s `OnTouch` to handle the click but introduced a different issue, the click event of the span was firing twice. It would fire once from `LinkMovementMethod`'s `onTouch(MotionEvent event)` and another time after from a different `onClick` call in the TextView's `onTouch`.

Removing the AutoLinkMask set by `autoLink="email|phone"` resolved the issue since the conditions wouldn't be met to fire the 2nd onClick call, but removed the builtin email/phone link handling. Looking for alternate solution. 

  Relevant Code in the TextView class:
  
  ``` 
  if (mMovement != null) {
      handled |= mMovement.onTouchEvent(this, (Spannable) mText, event);
  }

  final boolean textIsSelectable = isTextSelectable();
  if (touchIsFinished && mLinksClickable && mAutoLinkMask != 0 && textIsSelectable) {
      // The LinkMovementMethod which should handle taps on links has not been installed
      // on non editable text that support text selection.
      // We reproduce its behavior here to open links for these.
      ClickableSpan[] links = ((Spannable) mText).getSpans(getSelectionStart(),
              getSelectionEnd(), ClickableSpan.class);

      if (links.length > 0) {
          links[0].onClick(this);
          handled = true;
      }
  }
  ```
  
  Ideally, a second ClickableSpan click event shouldn't be fired.
  
  
