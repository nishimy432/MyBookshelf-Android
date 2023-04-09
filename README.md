# 書籍管理アプリ 

入社前課題で作成したアプリ。  
デザインや仕様だけ指定されていて、それを元に作成しました。
  
## ライブラリ
- [okHttp](https://square.github.io/okhttp/) : HTTP通信とSPDY通信をする  
- [retrofit](https://square.github.io/retrofit/) : RESTクライアント  
- [gson](https://github.com/google/gson#gson) : JSONデータとJavaオブジェクトを相互に変換する  
- [glide](https://github.com/bumptech/glide#glide) : 画像読み込み
  
## 参考
**全体**  
- [Kotlin スタイルガイド](https://developer.android.com/kotlin/style-guide)
- [mixi Androidアプリ開発研修【21新卒技術研修](https://youtu.be/91XQebVNnbI)
    - [スライド](https://www.slideshare.net/akkuma/21-android)
    - [リポジトリ](https://github.com/mixigroup/2021AndroidTraining)
- [エラー対処](https://qiita.com/tsukitoro0505/items/e16485918158f5d74d6f)
- [DIライブラリ「Hilt」のセットアップ&使い方(Kotlin)](https://qiita.com/uhooi/items/2a1ccb3fab9afd539203)
  
**Fragment**  
- [Fragment(公式)](https://developer.android.com/guide/components/fragments?hl=ja)
- [【Androidアプリ開発】fragmentによる画面遷移](https://akiakiblog.net/android-fragment/)
- [【Android】 Fragment から別の Fragment に画面遷移させてみる](https://akira-watson.com/android/fragment-fragment.html)
- [【Kotlin】Fragmentでの画面遷移時に値の受け渡しを実装をする](https://qiita.com/peter_parker/items/24b90e5c6e4bddffdc0f)
- [【Kotlin】NavigationとFragmentで画面遷移を管理する【Android】](https://swallow-incubate.com/archives/blog/20200902/)
- [Navigation Componentの使い方（概要〜画面遷移〜データの受け渡し編）](https://qiita.com/naoi/items/8384561d30111c8704b3)
- [【Navigation Component】 Back Stack の変化まとめ](https://qiita.com/oboenikui/items/81c099acf5c0cf5215ec)
- [【Android】Fragment間で値をやりとりする](https://qiita.com/m-coder/items/3a8e66d49f2830b09bf4)
- [ViewModel の概要 フラグメント間でデータを共有する](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=ja#sharing)
  
**DialogFragment**  
- [ダイアログ(公式)](https://developer.android.com/guide/topics/ui/dialogs?hl=ja)
- [DialogFragmentでカスタムダイアログを実装する](https://zenn.dev/m_coder/articles/article-zenn-custom-dialog-by-dialogfragment)
  
**ツールバー**  
- [【Android / Kotlin】Toolbar と戻るボタンを実装](https://qiita.com/tkmd35/items/a0af2b985491ddef7bc7)
- [【Kotlin】ToolBarに戻るボタンをつける + Navigation](https://qiita.com/orimomo/items/c710ce4c5c3d2553ef07)
- [FragmentへのToolbar 実装詳細](https://toconakis.tech/fragment-toolbar/)
  
**ナビゲーションバー**  
- アクティビティーテンプレート
- [BottomNavigationView入門](https://qiita.com/neonankiti/items/8f5a4b9039914192a948)
- [Android BottomNavigationView と Navigation併用時の状態保持](https://qiita.com/yoppie_x/items/3d0ce89230fdc6b61f4f)
- [iOSエンジニアがAndroidアプリをリリースするまで3 〜BottomNavigationViewのFragmentをViewPager2で切り替える〜](https://note.com/wai_knitting_app/n/ne6a32d38227c)  
  できなかった…
  
**バリデーションチェック関連**
- [【Kotlin】標準入力の値をチェックする – 数値をどう扱うのか？](https://pouhon.net/post-1219/1219/)
- [文字列と数値を変換する (toIntOrNull, toInt)](https://maku77.github.io/kotlin/numstr/convert.html)
- [String型変数が数字であるかチェックする方法の速さ比較](https://qiita.com/java-beginner/items/7a701394e64971a802d6)
- [日付のバリデーションチェック](https://chat-messenger.com/blog/java/dateformat-setlenient)
- [Kotlin で日時（日付／時刻）を扱う方法いろいろ](https://maku77.github.io/kotlin/misc/time.html)
  
**オプションメニュー**
- [アプリバーを使用する](https://developer.android.com/guide/fragments/appbar?hl=ja)
- [アプリバーの設定](https://developer.android.com/training/appbar/setting-up?hl=ja#kotlin)
  
**リスト**  
- [ミクシィ 21卒向け Android研修](https://www.slideshare.net/akkuma/21-android)
- [RecyclerView で動的リストを作成する](https://developer.android.com/guide/topics/ui/layout/recyclerview?hl=ja)
- [views-widgets-samples/RecyclerViewKotlin](https://github.com/android/views-widgets-samples/tree/main/RecyclerViewKotlin)
- [Android Kotlin基礎講座 07.1:RecyclerViewの基礎](https://codelabsjp.net/kotlin-android-training-07-1/)
- [Android Kotlin基礎講座 07.2:DiffUtilとRecycleViewのデータバインディング](https://codelabsjp.net/kotlin-android-training-07-2/)
- [Android Kotlin基礎講座 07.4:RecyclerViewのタップ処理](https://codelabsjp.net/kotlin-android-training-07-4/)
- [RecyclerViewでDiffUtilを使うならAsyncListDifferが便利](https://qiita.com/HiroyukiTamura/items/c31119e59c6738a2c991)
- [DiffUtilを非同期に使いRecyclerViewを更新する](https://qiita.com/nakker1218/items/271760a2cfa90e41fb4e)
- [RecyclerView の notifyItemChanged をもっと便利に使う](https://qiita.com/ralph/items/e56844976117d9883e34)
- [【Android】RecyclerView で無限スクロールを実装する](https://qiita.com/u-dai/items/0b1661e8329adf41830a)
  
**DataPicker**
- [選択ツール](https://developer.android.com/guide/topics/ui/controls/pickers?hl=ja)
- [DatePickerDialogで日付を取得](https://olee46.hatenablog.com/entry/2017/06/07/053010)
- [【Android & Kotlin】 DatePickerの実装](https://kotlin.akira-watson.com/android/date-picker.html)
- [DatePickerDialog と TimePickerDialog を使って日時選択をできるようにする](https://qiita.com/fumiyakawauso/items/dbb0517144f2a4400e6a)
- [Android（Kotlin）日付・時間選択ダイアログ](https://tech-blog.re-arc-lab.jp/posts/200903_android-datetimepicker/)

**画像を取得**

- [Androidでアプリ連携して画像を取得する（その１）](https://qiita.com/beyondseeker/items/72fd45dbcde7ad17faad)
- [【Android】画像を選択して、ImageViewに表示 in Kotlin](https://qiita.com/PoodleMaster/items/338bb732b979315ecd25)
- [写真を新しく撮るか、ギャラリから取得するか選択する方法](https://android.keicode.com/basics/multimedia-choose-pictures.php)
  結局 Intent に画像処理を飛ばさない方法を採用しました。

**その他**
- [文字列リソース(公式)](https://developer.android.com/guide/topics/resources/string-resource?hl=ja)
- [【Android / Kotlin】フォーム（EditText）入力の際、背景タップで表示キーボードを閉じる](https://qiita.com/tkmd35/items/7d2bd568bd646d0ce6a9)
- [Kotlin のコレクション使い方メモ](https://qiita.com/opengl-8080/items/36351dca891b6d9c9687)

**API**
- [Retrofitによるトークン認証| Android OAuth 2.0](https://ichi.pro/retrofit-niyoru-to-kun-ninsho-android-oauth-2-128065552750368)
- [Let's はじめてのRetrofit for Android in Kotlin（サンプルコード付き）](https://qiita.com/naoi/items/5036adc8d33638911deb)
- [【Androidアプリ開発】MVVM アーキテクチャで Retrofit を使って API を叩く](https://tech.mti.co.jp/entry/2020/03/31/163321)
- [【Android】MVVMで、Retrofit2 + Kotlin CoroutinesでHttp通信でqiitaの記事検索するやつ作った](https://qiita.com/yonce/items/eb4ba7cf38e52be6e16f)
- [非同期処理が終わるまで待機させる[Kotlin]](https://zenn.dev/tktcorporation/articles/be81213f6d0da3)
- [Retrofit + Kotlin Coroutines](https://tech.actindi.net/2019/08/02/090000)

**SafeArgs**

- [[Android] NavigationでSafeArgsを使って引数付き画面遷移をする](https://qiita.com/tktktks10/items/c78fe6f2083611676fbb)
- [Safe Argsのセットアップ&使い方(Kotlin)](https://qiita.com/uhooi/items/b73911afdc62ae188a3b)

**Glide**

- [Android用画像読み込みライブラリ、Glideを使ってみよう！](https://nulab.com/ja/blog/nulab/android-library-glide/)
- [Glide を Coil に置き換える](https://qiita.com/toastkidjp/items/8563df168fa37154ddbc)
