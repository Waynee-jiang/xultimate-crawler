package alibaba;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.danielli.xultimate.crawler.support.LinkbaseHandlerSupport;
import org.danielli.xultimate.crawler.support.urlresolver.JsoupAbstractUrlResolver;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.collections.CollectionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import alibaba.po.EduExp;
import alibaba.po.ImpressLabel;
import alibaba.po.Member;
import alibaba.po.Member.BloodType;
import alibaba.po.Member.EducatType;
import alibaba.po.Member.IdentityType;
import alibaba.po.Member.IncomeType;
import alibaba.po.Member.ReligionType;
import alibaba.po.Member.Sex;
import alibaba.po.WorkExp;
import alibaba.service.MemberService;

@Service("memberInfoUrlResolver")
public class MemberInfoUrlResolver extends JsoupAbstractUrlResolver {
	
	@Resource(name = "alibabaMemberServiceImpl")
	private MemberService memberService;
	
	private SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy年MM月dd日");
	
	private SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	
	private SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy.MM");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoUrlResolver.class);
	
	@Override
	public boolean support(String crawledeUrl) {
		return StringUtils.contains(crawledeUrl, "http://me.1688.com/info/");
	}
	
	@Override
	public int getPriority() {
		return 1000;
	}
	
	@Override
	public void handle(Document document, String linkId, String linkUrl) throws Exception {
		Member member = new Member();
		member.setAlibabaId(document.select(".mod.mod-i-masthead").select(".set-form").select("input[name=memberId]").val());
		
		if (StringUtils.isEmpty(member.getAlibabaId())) {
			throw new Exception("alibabaId为空");
		}
		
		try {
			Element element = document.select(".mod.mod-pf-basicInfo.bot-border").select(".item.name").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String name = element.child(1).text();
				if (StringUtils.isNotEmpty(name)) {
					member.setName(name);
					LOGGER.info("member:" + member.getAlibabaId() + "----Name----" + member.getName());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Name Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-basicInfo.bot-border").select(".item.sex").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String sexStr = element.child(1).text();
				if (StringUtils.isNotEmpty(sexStr)) {
					if (StringUtils.equals("男", sexStr)) {
						member.setSex(Sex.male);
					} else if (StringUtils.equals("女", sexStr)) {
						member.setSex(Sex.female);
					}
					LOGGER.info("member:" + member.getAlibabaId() + "----Sex----" + member.getSex());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Sex Error Message----" + e.getMessage());
		}

		try {
			Element element = document.select(".mod.mod-pf-basicInfo.bot-border").select(".item.morespace.identity").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String identity = element.child(1).text();
				if (StringUtils.isNotEmpty(identity)) {
					if (StringUtils.equals("企业单位", identity)) {
						member.setIdentity(IdentityType.qydw);
					} else if (StringUtils.equals("事业单位或社会团体", identity)) {
						member.setIdentity(IdentityType.sydw);
					} else if (StringUtils.equals("个体经营", identity)) {
						member.setIdentity(IdentityType.gtjy);
					} else if (StringUtils.equals("个人", identity)) {
						member.setIdentity(IdentityType.gr);
					}
					LOGGER.info("member:" + member.getAlibabaId() + "----Identity----" + member.getIdentity());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Identity Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-basicInfo.bot-border").select(".item.morespace.email").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String email = element.child(1).text();
				if (StringUtils.isNotEmpty(email)) {
					member.setEmail(email);
					LOGGER.info("member:" + member.getAlibabaId() + "----Email----" + member.getEmail());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Email Error Message----" + e.getMessage());
		}
		
		try {
			Elements elements = document.select(".mod.mod-pf-basicInfo.bot-border").select(".item.morespace.phone");
			if (elements.size() > 0) {
				Element element = elements.get(0);
				if (!CollectionUtils.isEmpty(element.children())) {
					String phone = element.child(1).text();
					if (StringUtils.isNotEmpty(phone)) {
						member.setPhone(phone);
						LOGGER.info("member:" + member.getAlibabaId() + "----Phone----" + member.getPhone());
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Phone Error Messagel----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-basicInfo.bot-border").select(".item.morespace.telephone").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String telephone = element.child(1).text();
				if (StringUtils.isNotEmpty(telephone)) {
					member.setTelephone(telephone);
					LOGGER.info("member:" + member.getAlibabaId() + "----Telephone----" + member.getTelephone());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Telephone Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-basicInfo.bot-border").select(".item.fax").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String fax = element.child(1).text();
				if (StringUtils.isNotEmpty(fax)) {
					member.setFax(fax);
					LOGGER.info("member:" + member.getAlibabaId() + "----Fax----" + member.getFax());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Fax Error Message----" + e.getMessage());
		}
		
		try {
			Elements elements = document.select(".mod.mod-my-account").select(".item.arae");
			if (elements.size() > 0) {
				String areaStr = elements.get(0).text();
				if (StringUtils.isNotEmpty(areaStr)) {
					String[] area = areaStr.split("-");
					member.setLiveProvince(area[0]);
					LOGGER.info("member:" + member.getAlibabaId() + "----LiveProvince----" + member.getLiveProvince());
					if (area.length > 1) {
						member.setLiveCity(area[1]);
						LOGGER.info("member:" + member.getAlibabaId() + "----LiveCity----" + member.getLiveCity());
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----LiveProvince Or LiveCity Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-basicInfo.bot-border").select(".item.morespace.address").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String address = element.child(1).text();
				if (StringUtils.isNotEmpty(address)) {
					member.setAddress(address);
					LOGGER.info("member:" + member.getAlibabaId() + "----Address----" + member.getAddress());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Address Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-basicInfo.bot-border").select(".item.pcode").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String pcode = element.child(1).text();
				if (StringUtils.isNotEmpty(pcode)) {
					member.setPcode(pcode);
					LOGGER.info("member:" + member.getAlibabaId() + "----Pcode----" + member.getPcode());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Pcode Error Message----" + e.getMessage());
		}
		
		try {
			String position = document.select(".mod.mod-my-account").select(".identity").get(0).text();
			if (StringUtils.isNotEmpty(position)) {
				member.setPosition(position);
				LOGGER.info("member:" + member.getAlibabaId() + "----Position----" + member.getPosition());
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Position Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-my-account").select(".item.company-info.last").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				Element e = element.child(0);
				String companyName = e.text();
				if (StringUtils.isNotEmpty(companyName)) {
					member.setCompanyName(companyName);
					LOGGER.info("member:" + member.getAlibabaId() + "----CompanyName----" + member.getCompanyName());
				}
				member.setCompanyUrlInAlibaba(e.attr("href"));
				LOGGER.info("member:" + member.getAlibabaId() + "----CompanyUrlInAlibaba----" + member.getCompanyUrlInAlibaba());
				Element ne = e.nextElementSibling();
				if (ne != null) {
					String v = ne.nextElementSibling().select(".red").text();
					if (StringUtils.isNotEmpty(v)) {
						member.setCreditYear(Integer.parseInt(v));
						LOGGER.info("member:" + member.getAlibabaId() + "----CreditYear----" + member.getCreditYear());
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----CompanyName Or CompanyUrlInAlibaba Or CreditYear Error Message----" + e.getMessage());
		}
		
		try {
			String headImageUrl = document.select(".mod.mod-pf-avatar").select(".img-wrapper").get(0).child(0).attr("src");
			member.setHeadImageUrl(headImageUrl);
			LOGGER.info("member:" + member.getAlibabaId() + "----HeadImageUrl----" + member.getHeadImageUrl());
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----HeadImageUrl Error Message----" + e.getMessage());
		}

		try {
			Element element = document.select(".mod.mod-pf-perInfo.bot-border").select(".item.phone.fd-clr").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String birthday = element.child(1).text();
				if (StringUtils.isNotEmpty(birthday)) {
					if (StringUtils.contains(birthday, "0001年")) {
						member.setBirthday(new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime());
					} else {
						member.setBirthday(dateFormat1.parse(birthday));
					}
					LOGGER.info("member:" + member.getAlibabaId() + "----Birthday----" + member.getBirthday());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Birthday Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-perInfo.bot-border").select(".item.telephone.fd-clr").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String bloodType = element.child(1).text();
				if (StringUtils.isNotEmpty(bloodType)) {
					if (StringUtils.equals("A型", bloodType)) {
						member.setBloodType(BloodType.A);
					} else if (StringUtils.equals("B型", bloodType)) {
						member.setBloodType(BloodType.B);
					} else if (StringUtils.equals("AB型", bloodType)) {
						member.setBloodType(BloodType.AB);
					} else if (StringUtils.equals("O型", bloodType)) {
						member.setBloodType(BloodType.O);
					} else  if (StringUtils.equals("N型", bloodType)) {
						member.setBloodType(BloodType.N);
					}
					LOGGER.info("member:" + member.getAlibabaId() + "----BloodType----" + member.getBloodType());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----BloodType Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-perInfo.bot-border").select(".item.fax.fd-clr").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String hometown = element.child(1).text();
				if (StringUtils.isNotEmpty(hometown)) {
					member.setHometown(hometown);
					LOGGER.info("member:" + member.getAlibabaId() + "----Hometown----" + member.getHometown());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Hometown Error Message----" + e.getMessage());
		}

		try {
			Element element = document.select(".mod.mod-pf-perInfo.bot-border").select(".item.income.morespace.fd-clr").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String income = element.child(1).text();
				if (StringUtils.isNotEmpty(income)) {
					if (StringUtils.equals("月收入10000以上", income)) {
						member.setIncome(IncomeType.q11);
					} else if (StringUtils.equals("月收入6001-10000", income)) {
						member.setIncome(IncomeType.q10);
					} else if (StringUtils.equals("月收入3001-6000", income)) {
						member.setIncome(IncomeType.q6);
					} else if (StringUtils.equals("月收入1001-3000", income)) {
						member.setIncome(IncomeType.q3);
					} else if (StringUtils.equals("月收入1000以下", income)) {
						member.setIncome(IncomeType.q1);
					} else if (StringUtils.equals("保密", income)) {
						member.setIncome(IncomeType.q0);
					}
					LOGGER.info("member:" + member.getAlibabaId() + "----Income----" + member.getIncome());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Income Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-perInfo.bot-border").select(".item.educat.fd-clr").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String educat = element.child(1).text();
				if (StringUtils.isNotEmpty(educat)) {
					if (StringUtils.equals("小学", educat)) {
						member.setEducat(EducatType.xx);
					} else if (StringUtils.equals("初中", educat)) {
						member.setEducat(EducatType.cz);
					} else if (StringUtils.equals("高中", educat)) {
						member.setEducat(EducatType.gz);
					} else if (StringUtils.equals("中专", educat)) {
						member.setEducat(EducatType.zz);
					} else if (StringUtils.equals("大专", educat)) {
						member.setEducat(EducatType.dz);
					} else if (StringUtils.equals("本科", educat)) {
						member.setEducat(EducatType.bk);
					} else if (StringUtils.equals("硕士", educat)) {
						member.setEducat(EducatType.ss);
					} else if (StringUtils.equals("博士", educat)) {
						member.setEducat(EducatType.bs);
					} else if (StringUtils.equals("保密", educat)) {
						member.setEducat(EducatType.bm);
					}  
					LOGGER.info("member:" + member.getAlibabaId() + "----Educat----" + member.getEducat());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Educat Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-perInfo.bot-border").select(".item.religion.morespace.fd-clr").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String religion = element.child(1).text();
				if (StringUtils.isNotEmpty(religion)) {
					if (StringUtils.equals("基督教", religion)) {
						member.setReligion(ReligionType.jdj);
					} else if (StringUtils.equals("伊斯兰教", religion)) {
						member.setReligion(ReligionType.yslj);
					} else if (StringUtils.equals("佛教", religion)) {
						member.setReligion(ReligionType.fj);
					} else if (StringUtils.equals("其他", religion)) {
						member.setReligion(ReligionType.qt);
					} else if (StringUtils.equals("无", religion)) {
						member.setReligion(ReligionType.w);
					}
					LOGGER.info("member:" + member.getAlibabaId() + "----Religion----" + member.getReligion());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Religion Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-perInfo.bot-border").select(".item.morespace.profile.fd-clr").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String profile = element.child(1).child(0).text();
				if (StringUtils.isNotEmpty(profile)) {
					member.setProfile(profile);
					LOGGER.info("member:" + member.getAlibabaId() + "----Profile----" + member.getProfile());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Religion Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-perInfo.bot-border").select(".item.morespace.interests.fd-clr").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String interests = element.child(1).text();
				if (StringUtils.isNotEmpty(interests)) {
					member.setInterests(interests);
					LOGGER.info("member:" + member.getAlibabaId() + "----Interests----" + member.getInterests());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Interests Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-perInfo.bot-border").select(".item.morespace.selfIntr.fd-clr").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String selfIntr = element.child(1).text();
				if (StringUtils.isNotEmpty(selfIntr)) {
					member.setSelfIntr(selfIntr);
					LOGGER.info("member:" + member.getAlibabaId() + "----SelfIntr----" + member.getSelfIntr());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----SelfIntr Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-account").select(".item.morespace.phone.memberAcc").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String memberAcc = element.child(1).text();
				if (StringUtils.isNotEmpty(memberAcc)) {
					member.setMemberAcc(memberAcc);
					LOGGER.info("member:" + member.getAlibabaId() + "----MemberAcc----" + member.getMemberAcc());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----MemberAcc Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-account").select(".item.morespace.registTime").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String registTimeStr = element.child(1).text();
				if (StringUtils.isNotEmpty(registTimeStr)) {
					member.setRegistTime(dateFormat2.parse(registTimeStr));
					LOGGER.info("member:" + member.getAlibabaId() + "----RegistTime----" + member.getRegistTime());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----RegistTime Error Message----" + e.getMessage());
		}
		
		try {
			Element element = document.select(".mod.mod-pf-account").select(".item.morespace.lastLogin").get(0);
			if (!CollectionUtils.isEmpty(element.children())) {
				String lastLoginStr = element.child(1).text();
				if (StringUtils.isNotEmpty(lastLoginStr)) {
					member.setLastLogin(dateFormat2.parse(lastLoginStr));
					LOGGER.info("member:" + member.getAlibabaId() + "----LastLogin----" + member.getLastLogin());
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----LastLogin Error Message----" + e.getMessage());
		}
		List<EduExp> eduExps = new ArrayList<EduExp>();
		
		try {
			Elements elements = document.select(".mod.mod-pf-education.bot-border");
			if (elements.size() > 0) { 
				Pattern pattern = Pattern.compile("^(.*)\\((.*)-(.*)\\)$");
				Elements es = elements.select(".item");
				for (Element element : es) {
					Matcher out = pattern.matcher(element.child(1).text());
					if (out.find()) {
						EduExp eduExp = new EduExp();
						eduExp.setSchoolName(out.group(1));
						LOGGER.info("member:" + member.getAlibabaId() + "----SchoolName----" + eduExp.getSchoolName());
						String startDateStr = out.group(2);
						if (StringUtils.isNotEmpty(startDateStr)) {
							eduExp.setStartDate(dateFormat3.parse(startDateStr));
							LOGGER.info("member:" + member.getAlibabaId() + "----StartDate----" + eduExp.getStartDate());
						}
						String endDateStr = out.group(3);
						
						if (StringUtils.isNotEmpty(endDateStr)) {
							eduExp.setEndDate(dateFormat3.parse(endDateStr));
							LOGGER.info("member:" + member.getAlibabaId() + "----EndDate----" + eduExp.getEndDate());
						}
						Element ne = element.nextElementSibling();
						if (ne != null && !CollectionUtils.isEmpty(ne.children())) {
							Element e1 = ne.child(0);
							if (e1.hasText()) {
								eduExp.setSpecialty(StringUtils.substringAfter(e1.text(), "："));
								LOGGER.info("member:" + member.getAlibabaId() + "----Specialty----" + eduExp.getSpecialty());
							}
							
							Element e2 = ne.child(1);
							if (e2.hasText()) {
								String educat = StringUtils.substringAfter(e2.text(), "：");
								if (StringUtils.isNotEmpty(educat)) {
									
									if (StringUtils.equals("小学", educat)) {
										eduExp.setEducat(EducatType.xx);
									} else if (StringUtils.equals("初中", educat)) {
										eduExp.setEducat(EducatType.cz);
									} else if (StringUtils.equals("高中", educat)) {
										eduExp.setEducat(EducatType.gz);
									} else if (StringUtils.equals("中专", educat)) {
										eduExp.setEducat(EducatType.zz);
									} else if (StringUtils.equals("大专", educat)) {
										eduExp.setEducat(EducatType.dz);
									} else if (StringUtils.equals("本科", educat)) {
										eduExp.setEducat(EducatType.bk);
									} else if (StringUtils.equals("硕士", educat)) {
										eduExp.setEducat(EducatType.ss);
									} else if (StringUtils.equals("博士", educat)) {
										eduExp.setEducat(EducatType.bs);
									} else if (StringUtils.equals("保密", educat)) {
										eduExp.setEducat(EducatType.bm);
									}  
									LOGGER.info("member:" + member.getAlibabaId() + "----Educat----" + eduExp.getEducat());
								}
							}
						}
						eduExps.add(eduExp);
					}
					
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Schools Error Message----" + member.getSex());
		}
		
		List<WorkExp> workExps = new ArrayList<WorkExp>();
		
		try {
			Elements elements = document.select(".mod.mod-pf-workExper");
			if (elements.size() > 0) { 
				Pattern pattern = Pattern.compile("^(.*)\\((.*)-(.*)\\)$");
				Elements es = elements.select(".item");
				for (Element element : es) {
					Matcher out = pattern.matcher(element.child(1).text());
					if (out.find()) {
						WorkExp workExp = new WorkExp();
						workExp.setCompanyName(out.group(1));
						LOGGER.info("member:" + member.getAlibabaId() + "----CompanyName----" + workExp.getCompanyName());
						String startDateStr = out.group(2);
						if (StringUtils.isNotEmpty(startDateStr)) {
							workExp.setStartDate(dateFormat3.parse(startDateStr));
							LOGGER.info("member:" + member.getAlibabaId() + "----StartDate----" + workExp.getStartDate());
						}
						String endDateStr = out.group(3);
						if (StringUtils.isNotEmpty(endDateStr)) {
							workExp.setEndDate(dateFormat3.parse(endDateStr));
							LOGGER.info("member:" + member.getAlibabaId() + "----EndDate----" + workExp.getEndDate());
						}
						
						Element ne = element.nextElementSibling();
						if (ne != null && !CollectionUtils.isEmpty(ne.children())) {
							Element e1 = ne.child(0);
							if (e1.hasText()) {
								workExp.setPosition(StringUtils.substringAfter(e1.text(), "："));
								LOGGER.info("member:" + member.getAlibabaId() + "----Position----" + workExp.getPosition());
							}

							Element e2 = ne.child(1);
							if (e2.hasText()) {
								workExp.setDepartment(StringUtils.substringAfter(e2.text(), "："));
								LOGGER.info("member:" + member.getAlibabaId() + "----Department----" + workExp.getDepartment());
							}
							
							Element e3 = ne.child(2);
							if (e3.hasText()) {
								workExp.setIndustry(StringUtils.substringAfter(e3.text(), "："));
								LOGGER.info("member:" + member.getAlibabaId() + "----Industry----" + workExp.getIndustry());
							}
							
							Element e4 = ne.child(3);
							if (e4.hasText()) {
								workExp.setScale(StringUtils.substringAfter(e4.text(), "："));
								LOGGER.info("member:" + member.getAlibabaId() + "----Scale----" + workExp.getScale());
							}
							
							Element e5 = ne.child(4);
							if (e5.hasText()) {
								workExp.setRemark(StringUtils.substringAfter(e5.text(), "："));
								LOGGER.info("member:" + member.getAlibabaId() + "----Remark----" + workExp.getRemark());
							}
						}
						
						workExps.add(workExp);
					}
				}
				
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----Companys Error Message----" + e.getMessage());
		}
		
		List<ImpressLabel> impressLabels = new ArrayList<ImpressLabel>();
		try {
			Elements elements = document.select(".mod.mod-pf-impressLabel.bot-border");
			if (elements.size() > 0) { 
				Elements es = elements.select(".fui-txt");
				for (Element element : es) {
					ImpressLabel impressLabel = new ImpressLabel();
					impressLabel.setLabelName(element.text());
					LOGGER.info("member:" + member.getAlibabaId() + "----LabelName----" + impressLabel.getLabelName());
					impressLabels.add(impressLabel);
				}
			}
		} catch (Exception e) {
			LOGGER.error("member:" + member.getAlibabaId() + "----ImpressLabels Error Message----" + e.getMessage());
		}   
		
		memberService.saveMemberInfo(member, eduExps, workExps, impressLabels);
		
		Element element = document.select(".mod.mod-pf-avatar").get(0);
		
		List<Map<String, Object>> linkUrls = new ArrayList<Map<String, Object>>();
		Element attenElement = element.select(".atten.rborder.fd-left").get(0).child(0);
		String attenAddress = attenElement.attr("href");
		Map<String, Object> attenlinkUrlMap = new HashMap<>();
		attenlinkUrlMap.put(LinkbaseHandlerSupport.LINK_COLUMN_NAME, attenAddress);
		attenlinkUrlMap.put(LinkbaseHandlerSupport.PARENT_LINK_COLUMN_NAME, linkUrl);
		attenlinkUrlMap.put(LinkbaseHandlerSupport.LINK_CREATE_TIME, System.currentTimeMillis());
		linkUrls.add(attenlinkUrlMap);

		Element fansElement = element.select(".fans.rborder.fd-left").get(0).child(0);
		String fansAddress = fansElement.attr("href");
		Map<String, Object> fanslinkUrlMap = new HashMap<>();
		fanslinkUrlMap.put(LinkbaseHandlerSupport.LINK_COLUMN_NAME, fansAddress);
		fanslinkUrlMap.put(LinkbaseHandlerSupport.PARENT_LINK_COLUMN_NAME, linkUrl);
		fanslinkUrlMap.put(LinkbaseHandlerSupport.LINK_CREATE_TIME, System.currentTimeMillis());
		linkUrls.add(fanslinkUrlMap);
		
		linkbaseHandler.addLinkUrls(linkUrls);
	}
}
