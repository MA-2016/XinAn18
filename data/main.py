
import xmltodict
from py2neo import Graph, Node, Relationship

# open data file
with open('ComprehensiveCAPECDictionary.xml', 'rb') as f:
    data = xmltodict.parse(f.read().decode('utf8'))
    root = data['Attack_Pattern_Catalog']

graph = Graph('http://localhost:7474', username='neo4j', password='root')



url = 'http://capec.mitre.org/data/definitions/1000.html'
r = get(url)
soup = BeautifulSoup(r.text, 'html.parser')

tabeldetail = soup.find_all('div', class_ = 'tabledetail')[1]
indent = tabeldetail.find('div', class_ = 'indent')
x = [i for i in indent.children][6]

def dbAccess():
    from py2neo import Graph, Node, Relationship
    def output(node, p_node):
        for item in node['children']:
            c_node = Node(name = item['name'], eventId = int(item['id']), eventType = item['type'])
            c_node.add_label('Event')
            graph.create(c_node)
            # (child)-[r:Derivation]->(parent)
            graph.create(Relationship(c_node, 'Derivation', p_node))
            output(item, c_node)
    graph = Graph("http://localhost:7474",username = "neo4j",password = "root")
    for item in tree:
        p_node = Node(name = item['name'], eventId = int(item['id']), eventType = item['type'], beScene = True)
        p_node.add_label('Scene')
        graph.create(p_node)
        output(item, p_node)

def handleId(cid):
    if cid.startswith('('):
        cid = cid[1:]
    if cid.endswith(')'):
        cid = cid[:-1]
    return cid

def tranverse(target, base):
    for item in target.children:
        span = item.findChild('span')
        if span.attrs.get('id') != None:
            # not leaf node
            # if span and span.next_sibling and span.next_sibling.next_sibling:
            type = span.next_sibling.span.img.attrs.get('alt').replace(' ', '')
            span = span.next_sibling.next_sibling
            node = dict(name=span.a.getText(), id=handleId(span.span.i.getText()), children=[], type=type)
            base['children'].append(node)
            if item.div and item.div.span: tranverse(item.div.span, node)
        else:
            # be leaf node
            type = span.span.img.attrs.get('alt').replace(' ', '')
            span = span.next_sibling
            node = dict(name=span.a.getText(), id=handleId(span.span.i.getText()), children=[], type=type)
            base['children'].append(node)

categories = {}
for c in root['Categories']['Category']:
    categories[c['@ID']] = {
        'id': int(c['@ID']),
        'name': c['@Name'],
        'children': [],
        'type': 'Category'
    }

for ap in root['Attack_Patterns']['Attack_Pattern']:
    node = {
        'name': ap['@Name'] if '@Name' in ap else '',
        'id': ap['@ID'] if '@ID' in ap else '',
        'type': ap['Abstraction'] if '@Abstraction' in ap else '',
        'children': [],
    }

dbAccess()
