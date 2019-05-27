
			<!-- jacoco -->
			<plugin>
				<groupId>org.jacoco</groupId>
				<artifactId>jacoco-maven-plugin</artifactId>
				<version>0.7.8</version>
				<!--这里的execution ,每一个执行的goal,对应的id必须是唯一的-->
				<executions>
					<execution>
						<id>prepare-agent</id>
						<goals>
							<goal>prepare-agent</goal>
						</goals>
					</execution>
					<!--这个check:对代码进行检测，控制项目构建成功还是失败-->
					<execution>
						<id>check</id>
						<goals>
							<goal>check</goal>
						</goals>
					</execution>
					<!--这个report:对代码进行检测，然后生成index.html在 target/site/index.html中可以查看检测的详细结果-->
					<execution>
						<id>report</id>
						<phase>prepare-package</phase>
						<goals>
							<goal>report</goal>
						</goals>
					</execution>
					<!-- Configuration 里面写配置信息 -->
					<configuration>
						<!-- rules里面指定覆盖规则 -->
						<rules>
						<rule implementation="org.jacoco.maven.RuleConfiguration">
							<element>BUNDLE</element>
							<limits>
							　　<!-- 指定方法覆盖到80% -->
								<limit implementation="org.jacoco.report.check.Limit">
									<counter>METHOD</counter>
									<value>COVEREDRATIO</value>
									<minimum>0.80</minimum>
								</limit>
								<!-- 指定指令覆盖到80% -->
								<limit implementation="org.jacoco.report.check.Limit">
									<counter>INSTRUCTION</counter>
									<value>COVEREDRATIO</value>
									<minimum>0.80</minimum>
								</limit>
								<!-- 指定行覆盖到80% -->
								<limit implementation="org.jacoco.report.check.Limit">
									<counter>LINE</counter>
									<value>COVEREDRATIO</value>
									<minimum>0.80</minimum>
								</limit>
								<!-- 指定类覆盖到100%，不能遗失任何类 -->
								<limit implementation="org.jacoco.report.check.Limit">
									<counter>CLASS</counter>
									<value>MISSEDCOUNT</value>
									<maximum>0</maximum>
								</limit>
							</limits>
						</rule>
					</rules>
					</configuration>
				</executions>
			</plugin>