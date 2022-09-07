showUser();

function showUser() {
    const userTable = document.querySelector('#tableUser tbody');
    let temp1 = ""
    fetch("http://localhost:8080/api/authorizeUser")
        .then(response => response.json())
        .then(user => {
            temp1 += `
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.roles.map(e => " " + e.name.substring(5))}</td>
            </tr>    
            `;
            userTable.innerHTML = temp1;
        })
}

async function getUser(id) {
    let response = await fetch("http://localhost:8080/api/users/" + id);
    return await response.json();
}