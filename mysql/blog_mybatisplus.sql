/*
Navicat MySQL Data Transfer

Source Server         : 39.97.230.224_3306
Source Server Version : 80022
Source Host           : 39.97.230.224:3306
Source Database       : blog_mybatisplus

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2020-11-02 22:52:21
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_blog`
-- ----------------------------
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `appreciation` bit(1) NOT NULL,
  `commentabled` bit(1) NOT NULL,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_date` datetime NOT NULL,
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `first_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `flag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `published` bit(1) NOT NULL,
  `recommend` bit(1) NOT NULL,
  `share_statement` bit(1) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `update_date` datetime NOT NULL,
  `views` int NOT NULL,
  `type_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `tag_ids` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK292449gwg5yf7ocdlmswv9w4j` (`type_id`),
  KEY `FK8ky5rrsxh01nkhctmo7d48p82` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_blog
-- ----------------------------
INSERT INTO `t_blog` VALUES ('1', '', '', '## yaml语法\r\n\r\n**1、对象、Map**\r\n\r\n```yaml\r\nfriends:\r\n	lastName: zyh\r\n	age: 21\r\n```\r\n\r\n​	行内写法\r\n\r\n```yaml\r\nfriends: {lastName: zyh,age: 21}\r\n```\r\n\r\n**2、数组（List、Set）**\r\n\r\n```yaml\r\npets:\r\n - cat\r\n - dog\r\n - pig\r\n```\r\n\r\n​	行内写法\r\n\r\n```yaml\r\npets: [cat,dog,pig]\r\n```\r\n\r\n**3、整合写法**\r\n\r\n```yaml\r\nperson:\r\n    lastName: hello\r\n    age: 18\r\n    boss: false\r\n    birth: 2017/12/12\r\n    maps: {k1: v1,k2: 12}\r\n    lists:\r\n      - lisi\r\n      - zhaoliu\r\n    dog:\r\n      name: 小狗\r\n      age: 12\r\n```\r\n\r\n​	javaBean：\r\n\r\n```java\r\n/**\r\n * 将配置文件中配置的每一个属性的值，映射到这个组件中\r\n * @ConfigurationProperties：告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定；\r\n *      prefix = \"person\"：配置文件中哪个下面的所有属性进行一一映射\r\n *\r\n * 只有这个组件是容器中的组件，才能容器提供的@ConfigurationProperties功能；\r\n *\r\n */\r\n@Component\r\n@ConfigurationProperties(prefix = \"person\")\r\npublic class Person {\r\n\r\n    private String lastName;\r\n    private Integer age;\r\n    private Boolean boss;\r\n    private Date birth;\r\n\r\n    private Map<String,Object> maps;\r\n    private List<Object> lists;\r\n    private Dog dog;\r\n```\r\n\r\n​	要是在书写yaml文件时不提示，可以导入相关的依赖，就有提示了\r\n\r\n```xml\r\n<!--导入配置文件处理器，配置文件进行绑定就会有提示-->\r\n		<dependency>\r\n			<groupId>org.springframework.boot</groupId>\r\n			<artifactId>spring-boot-configuration-processor</artifactId>\r\n			<optional>true</optional>\r\n		</dependency>\r\n```\r\n\r\n**4、@Value获取值和@ConfigurationProperties获取值的比较**\r\n\r\n|                                        | @ConfigurationProperties | @Value     |\r\n| -------------------------------------- | ------------------------ | ---------- |\r\n| 功能                                   | 批量注入配置文件中的属性 | 一个个指定 |\r\n| 松散绑定：lastName=last_name/last-name | 支持                     | 不支持     |\r\n| SpEL语法：@Value(\"#{11*2}\")            | 不支持                   | 支持       |\r\n| JSR303数据校验                         | 支持                     | 不支持     |\r\n| 复杂类型封装：Map、对象                | 支持                     | 不支持     |\r\n\r\n**5、配置文件占位符**\r\n\r\n```properties\r\n${random.value}、${random.int}、${random.long}\r\n${random.int(10)}、${random.int[1024,65536]}\r\n\r\nperson.last-name=张三${random.uuid}\r\nperson.age=${random.int}\r\nperson.birth=2017/12/15\r\nperson.boss=false\r\nperson.maps.k1=v1\r\nperson.maps.k2=14\r\nperson.lists=a,b,c\r\nperson.dog.name=${person.hello:hello}_dog\r\nperson.dog.age=15\r\n```\r\n\r\n', '2020-10-25 21:04:32', '这是我总结的一些关于yaml使用的语法，以往能够帮到你们', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598096279663&di=5440609c84c47e18e9d80b1a6a55a313&imgtype=0&src=http%3A%2F%2Fpic2.58.com%2Fzp_images%2Fallimg%2F120526%2F1_120526093403_1.png', '原创', '', '', '', 'yaml语法', '2020-10-25 21:04:32', '2', '1', '1', '1');
INSERT INTO `t_blog` VALUES ('2', '', '', '## 封装http报文\r\n\r\n### get请求的例子\r\n\r\n1、测试类中封装http请求\r\n\r\n```java\r\n@Test\r\npublic void testdoGet(){\r\n    String httpUrl = \"http://localhost:8081/\";\r\n    String responsePage = null;\r\n    responsePage = doGet(httpUrl);\r\n    System.out.println(responsePage);\r\n}\r\n\r\npublic static String doGet(String httpUrl) {\r\n        HttpURLConnection connection = null;\r\n        InputStream inputStream = null;\r\n        BufferedReader bufferedReader = null;\r\n        String result = null;// 返回结果字符串\r\n        try {\r\n            // 创建远程url连接对象\r\n            URL url = new URL(httpUrl);\r\n            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类\r\n            connection = (HttpURLConnection) url.openConnection();\r\n            // 设置连接方式：get\r\n            connection.setRequestMethod(\"GET\");\r\n            // 设置连接主机服务器的超时时间：15000毫秒\r\n            connection.setConnectTimeout(15000);\r\n            // 设置读取远程返回的数据时间：60000毫秒\r\n            connection.setReadTimeout(60000);\r\n            // 发送请求\r\n            connection.connect();\r\n            // 通过connection连接，获取输入流\r\n            if (connection.getResponseCode() == 200) {\r\n                inputStream = connection.getInputStream();\r\n                // 封装输入流is，并指定字符集\r\n                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, \"UTF-8\"));\r\n                // 存放数据\r\n                StringBuffer sb = new StringBuffer();\r\n                String temp = null;\r\n                while ((temp = bufferedReader.readLine()) != null) {\r\n                    sb.append(temp);\r\n                    sb.append(\"\\r\\n\");\r\n                }\r\n                result = sb.toString();\r\n            }\r\n        } catch (MalformedURLException e) {\r\n            e.printStackTrace();\r\n        } catch (IOException e) {\r\n            e.printStackTrace();\r\n        } finally {\r\n            // 关闭资源\r\n            if (null != bufferedReader) {\r\n                try {\r\n                    bufferedReader.close();\r\n                } catch (IOException e) {\r\n                    e.printStackTrace();\r\n                }\r\n            }\r\n\r\n            if (null != inputStream) {\r\n                try {\r\n                    inputStream.close();\r\n                } catch (IOException e) {\r\n                    e.printStackTrace();\r\n                }\r\n            }\r\n            // 关闭远程连接\r\n            connection.disconnect();\r\n        }\r\n\r\n        return result;\r\n    }\r\n```\r\n\r\n2、controller层\r\n\r\n```java\r\n@Controller\r\npublic class TypeController {\r\n\r\n    @Autowired\r\n    private TypeDao typeDao;\r\n\r\n    @GetMapping(\"/\")\r\n    @ResponseBody\r\n    public String index(){\r\n        return \"{\\\"Response\\\":{\\\"ErrorInfo\\\":{\\\"Hint\\\":\\\"\\\",\\\"Message\\\":\\\"失败\\\",\\\"Code\\\":\\\"0000\\\"},\\\"RetInfo\\\":{}}}\";\r\n    }\r\n}\r\n```\r\n\r\n3、输出结果\r\n\r\n```java\r\n{\"Response\":{\"ErrorInfo\":{\"Hint\":\"\",\"Message\":\"失败\",\"Code\":\"0000\"},\"RetInfo\":{}}}\r\n\r\n```\r\n\r\n\r\n\r\n### post请求例子\r\n\r\n1、测试类中封装http请求\r\n\r\n```java\r\n@Test\r\npublic void testdoPost() throws Exception {\r\n    String httpUrl = \"http://localhost:8081/insert\";\r\n    JSONObject jsonObject = new JSONObject();\r\n    jsonObject.put(\"id\",10);\r\n    jsonObject.put(\"name\",\"haha\");\r\n    String data = jsonObject.toJSONString();\r\n    String responsePage = null;\r\n    responsePage = doPost(httpUrl,data);\r\n    System.out.println(responsePage);\r\n}\r\n\r\npublic static String doPost(String httpUrl, String data) throws Exception{\r\n        HttpURLConnection connection = null;\r\n        InputStream is = null;\r\n        OutputStream os = null;\r\n        BufferedReader br = null;\r\n        String result = null;\r\n        try {\r\n            URL url = new URL(httpUrl);\r\n            // 通过远程url连接对象打开连接\r\n            connection = (HttpURLConnection) url.openConnection();\r\n            // 设置连接请求方式\r\n            connection.setRequestMethod(\"POST\");\r\n            // 设置连接主机服务器超时时间：15000毫秒\r\n            connection.setConnectTimeout(15000);\r\n            // 设置读取主机服务器返回数据超时时间：60000毫秒\r\n            connection.setReadTimeout(60000);\r\n\r\n            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true\r\n            connection.setDoOutput(true);\r\n            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无\r\n            connection.setDoInput(true);\r\n            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。\r\n            connection.setRequestProperty(\"Content-Type\", \"application/json\");\r\n            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0\r\n            //connection.setRequestProperty(\"Authorization\", \"Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0\");\r\n            // 通过连接对象获取一个输出流\r\n            os = connection.getOutputStream();\r\n            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的\r\n            os.write(data.getBytes());\r\n            // 通过连接对象获取一个输入流，向远程读取\r\n            if (connection.getResponseCode() == 200) {\r\n\r\n                is = connection.getInputStream();\r\n                // 对输入流对象进行包装:charset根据工作项目组的要求来设置\r\n                br = new BufferedReader(new InputStreamReader(is, \"UTF-8\"));\r\n\r\n                StringBuffer sbf = new StringBuffer();\r\n                String temp = null;\r\n                // 循环遍历一行一行读取数据\r\n                while ((temp = br.readLine()) != null) {\r\n                    sbf.append(temp);\r\n                    sbf.append(\"\\r\\n\");\r\n                }\r\n                result = sbf.toString();\r\n            }\r\n        } catch (MalformedURLException e) {\r\n            e.printStackTrace();\r\n        } catch (IOException e) {\r\n            e.printStackTrace();\r\n        } finally {\r\n            // 关闭资源\r\n            if (null != br) {\r\n                try {\r\n                    br.close();\r\n                } catch (IOException e) {\r\n                    e.printStackTrace();\r\n                }\r\n            }\r\n            if (null != os) {\r\n                try {\r\n                    os.close();\r\n                } catch (IOException e) {\r\n                    e.printStackTrace();\r\n                }\r\n            }\r\n            if (null != is) {\r\n                try {\r\n                    is.close();\r\n                } catch (IOException e) {\r\n                    e.printStackTrace();\r\n                }\r\n            }\r\n            // 断开与远程地址url的连接\r\n            connection.disconnect();\r\n        }\r\n        return result;\r\n    }\r\n```\r\n\r\n2、controller\r\n\r\n```java\r\n@Controller\r\npublic class TypeController {\r\n\r\n    @Autowired\r\n    private TypeDao typeDao;\r\n\r\n    @PostMapping(\"/insert\")\r\n    @ResponseBody\r\n    public String insert(@RequestBody Type type) {\r\n        typeDao.insert(type);\r\n        return \"{\\\"Response\\\":{\\\"ErrorInfo\\\":{\\\"Hint\\\":\\\"\\\",\\\"Message\\\":\\\"失败\\\",\\\"Code\\\":\\\"0000\\\"},\\\"RetInfo\\\":{}}}\";\r\n    }\r\n}\r\n```\r\n\r\n3、输出结果\r\n\r\n```java\r\n{\"Response\":{\"ErrorInfo\":{\"Hint\":\"\",\"Message\":\"失败\",\"Code\":\"0000\"},\"RetInfo\":{}}}\r\n\r\n```\r\n\r\n', '2020-10-25 21:57:27', '这是封装http报文的两个例子，get和post，以往可以帮到你们', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598613031241&di=b20729e526730dd0c0766dd5c0c7d396&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2F049dac642b7958eeda71a7f24d38a645d3bf804a.jpg', '原创', '', '', '', '封装http报文', '2020-10-25 21:57:27', '0', '4', '1', '2,3');
INSERT INTO `t_blog` VALUES ('3', '', '', '我仍然\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n仍她和你\r\n\r\n\r\n\r\nrgthy \r\n\r\n\r\nrhtyjujmymj就， \r\n\r\n\r\nmy林，，yu', '2020-10-25 21:58:13', '的玩法的分布天花板，就看看文件柜话题和界面不同仁和家园吗㔿', 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=218969035,460888786&fm=26&gp=0.jpg', '原创', '', '', '', '去单位房', '2020-10-25 21:58:13', '0', '5', '1', '4,6');

-- ----------------------------
-- Table structure for `t_blog_tags`
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_tags`;
CREATE TABLE `t_blog_tags` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `blog_id` bigint DEFAULT NULL,
  `tag_id` bigint DEFAULT NULL,
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5feau0gb4lq47fdb03uboswm8` (`tag_id`),
  KEY `FKh4pacwjwofrugxa9hpwaxg6mr` (`blog_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_blog_tags
-- ----------------------------
INSERT INTO `t_blog_tags` VALUES ('1', '1', '1', 'thymeleaf');
INSERT INTO `t_blog_tags` VALUES ('2', '2', '2', 'post');
INSERT INTO `t_blog_tags` VALUES ('3', '2', '3', 'get');
INSERT INTO `t_blog_tags` VALUES ('4', '3', '4', '云服务器');
INSERT INTO `t_blog_tags` VALUES ('5', '3', '6', 'OSS');

-- ----------------------------
-- Table structure for `t_comment`
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` bigint NOT NULL,
  `admin_comment` bit(1) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `blog_id` bigint DEFAULT NULL,
  `parent_comment_id` bigint DEFAULT NULL,
  `replied_nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKke3uogd04j4jx316m1p51e05u` (`blog_id`),
  KEY `FK4jj284r3pb7japogvo6h72q95` (`parent_comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `t_tag`
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES ('1', 'thymeleaf');
INSERT INTO `t_tag` VALUES ('2', 'post');
INSERT INTO `t_tag` VALUES ('3', 'get');
INSERT INTO `t_tag` VALUES ('4', '云服务器');
INSERT INTO `t_tag` VALUES ('5', 'Markdown');
INSERT INTO `t_tag` VALUES ('6', 'OSS');

-- ----------------------------
-- Table structure for `t_type`
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES ('1', 'springboot');
INSERT INTO `t_type` VALUES ('2', 'Linux');
INSERT INTO `t_type` VALUES ('3', 'MySQL');
INSERT INTO `t_type` VALUES ('4', '封装http报文');
INSERT INTO `t_type` VALUES ('5', '实习');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'https://unsplash.it/100/100?image=1005', '2020-10-25 19:55:50.000000', '1430612680@qq.com', '小左', '202cb962ac59075b964b07152d234b70', '1', '2020-10-26 19:55:54.000000', 'zyh');
