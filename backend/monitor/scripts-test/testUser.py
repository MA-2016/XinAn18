
import json
from requests import get, Session

base = 'http://localhost:8001/user/'

session = Session()

account = '1242005392@qq.com'

def output(r):
    print(r.status_code)
    print(r.content)

def testValidate():
    rep = session.get(base + 'user/validate?account=admin&password=123')
    if json.loads(rep.content)['code'] != 200:
        raise Exception(rep.content)

def testGetCheckCode():
    r = session.get(base + 'getCheckCode')
    with open('/home/lijingwei/Desktop/checkCode.jpeg', 'wb') as f:
        f.write(r.content)

def testRegis(checkCode):
    r = session.get(base + 'regis?account=' + account + '&password=123&checkCode=' + checkCode)
    output(r)

def testActivate(activateIdentifier):
    r = session.get(base + 'activate?account=' + account + '&activateIdentifier=' + activateIdentifier)
    output(r)