resolvers += "informaticon" at "https://maven.devops.informaticon.com"
credentials += Credentials(Path.userHome / ".sbt" / ".credentials")

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.19")
addSbtPlugin("com.typesafe.play" % "sbt-play-ebean" % "6.2.0")

addSbtPlugin("com.informaticon" % "lib.sbt.base.npm-webpack-plugin" % "0.3.1")