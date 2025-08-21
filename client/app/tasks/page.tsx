"use client"

import { useState, useEffect } from "react"
import { Card, CardBody, Input, Select, SelectItem, Button, Chip, Pagination } from "@nextui-org/react"
import { Search, Filter, MapPin, DollarSign, Clock, User } from "lucide-react"
import NavigationBar from "@/components/Navbar"
import { useAuth } from "@/contexts/AuthContext"

interface Task {
  id: string
  title: string
  description: string
  budget: number
  category: string
  location: string
  urgency: "LOW" | "MEDIUM" | "HIGH"
  postedBy: string
  postedAt: string
  status: string
}

export default function TasksPage() {
  const { user } = useAuth()
  const [tasks, setTasks] = useState<Task[]>([])
  const [filteredTasks, setFilteredTasks] = useState<Task[]>([])
  const [searchTerm, setSearchTerm] = useState("")
  const [categoryFilter, setCategoryFilter] = useState("")
  const [currentPage, setCurrentPage] = useState(1)
  const tasksPerPage = 6

  const categories = [
    { key: "all", label: "All Categories" },
    { key: "shopping", label: "Shopping" },
    { key: "cleaning", label: "Cleaning" },
    { key: "delivery", label: "Delivery" },
    { key: "handyman", label: "Handyman" },
    { key: "pet-care", label: "Pet Care" },
    { key: "moving", label: "Moving" },
  ]

  useEffect(() => {
    // Mock data - replace with actual API call
    const mockTasks: Task[] = [
      {
        id: "1",
        title: "Grocery Shopping at Whole Foods",
        description: "Need someone to buy organic groceries from my shopping list. Should take about 1-2 hours.",
        budget: 45,
        category: "shopping",
        location: "Downtown",
        urgency: "MEDIUM",
        postedBy: "Sarah M.",
        postedAt: "2024-01-15",
        status: "PENDING",
      },
      {
        id: "2",
        title: "Deep Clean 2-Bedroom Apartment",
        description:
          "Looking for professional cleaning service for my apartment. Kitchen, bathrooms, and living areas.",
        budget: 120,
        category: "cleaning",
        location: "Midtown",
        urgency: "LOW",
        postedBy: "Mike R.",
        postedAt: "2024-01-14",
        status: "PENDING",
      },
      {
        id: "3",
        title: "Package Pickup and Delivery",
        description: "Need someone to pick up a package from UPS store and deliver to my office.",
        budget: 25,
        category: "delivery",
        location: "Business District",
        urgency: "HIGH",
        postedBy: "Lisa K.",
        postedAt: "2024-01-15",
        status: "PENDING",
      },
      {
        id: "4",
        title: "Fix Leaky Faucet",
        description:
          "Kitchen faucet is leaking and needs repair. Should be a quick fix for someone with plumbing experience.",
        budget: 80,
        category: "handyman",
        location: "Suburbs",
        urgency: "MEDIUM",
        postedBy: "John D.",
        postedAt: "2024-01-13",
        status: "PENDING",
      },
      {
        id: "5",
        title: "Dog Walking Service",
        description: "Need someone to walk my golden retriever for 30 minutes. Very friendly dog!",
        budget: 20,
        category: "pet-care",
        location: "Park Area",
        urgency: "LOW",
        postedBy: "Emma W.",
        postedAt: "2024-01-15",
        status: "PENDING",
      },
      {
        id: "6",
        title: "Help Moving Furniture",
        description: "Need help moving some furniture to a new apartment. Truck provided, just need muscle!",
        budget: 100,
        category: "moving",
        location: "Various",
        urgency: "HIGH",
        postedBy: "David L.",
        postedAt: "2024-01-14",
        status: "PENDING",
      },
    ]

    setTasks(mockTasks)
    setFilteredTasks(mockTasks)
  }, [])

  useEffect(() => {
    let filtered = tasks

    if (searchTerm) {
      filtered = filtered.filter(
        (task) =>
          task.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
          task.description.toLowerCase().includes(searchTerm.toLowerCase()),
      )
    }

    if (categoryFilter && categoryFilter !== "all") {
      filtered = filtered.filter((task) => task.category === categoryFilter)
    }

    setFilteredTasks(filtered)
    setCurrentPage(1)
  }, [searchTerm, categoryFilter, tasks])

  const getUrgencyColor = (urgency: string) => {
    switch (urgency) {
      case "LOW":
        return "success"
      case "MEDIUM":
        return "warning"
      case "HIGH":
        return "danger"
      default:
        return "default"
    }
  }

  const handleApplyForTask = async (taskId: string) => {
    if (!user || user.role !== "ERRAND_BOY") {
      alert("Only errand boys can apply for tasks")
      return
    }

    try {
      const response = await fetch("http://localhost:9123/task-attempts", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          taskId: Number.parseInt(taskId),
          errandBoyId: Number.parseInt(user.id),
          wasAccepted: false,
        }),
      })

      if (response.ok) {
        alert("Application submitted successfully!")
      }
    } catch (error) {
      console.error("Error applying for task:", error)
    }
  }

  // Pagination
  const indexOfLastTask = currentPage * tasksPerPage
  const indexOfFirstTask = indexOfLastTask - tasksPerPage
  const currentTasks = filteredTasks.slice(indexOfFirstTask, indexOfLastTask)
  const totalPages = Math.ceil(filteredTasks.length / tasksPerPage)

  return (
    <div className="min-h-screen bg-gray-50">
      <NavigationBar />

      <div className="max-w-7xl mx-auto p-6">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-2">Available Tasks</h1>
          <p className="text-gray-600">Find tasks that match your skills and schedule</p>
        </div>

        {/* Filters */}
        <Card className="mb-6">
          <CardBody>
            <div className="flex flex-col md:flex-row gap-4">
              <Input
                placeholder="Search tasks..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                startContent={<Search className="w-4 h-4 text-gray-400" />}
                className="md:flex-1"
              />
              <Select
                placeholder="Filter by category"
                value={categoryFilter}
                onChange={(e) => setCategoryFilter(e.target.value)}
                startContent={<Filter className="w-4 h-4 text-gray-400" />}
                className="md:w-64"
              >
                {categories.map((category) => (
                  <SelectItem key={category.key} value={category.key}>
                    {category.label}
                  </SelectItem>
                ))}
              </Select>
            </div>
          </CardBody>
        </Card>

        {/* Tasks Grid */}
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
          {currentTasks.map((task) => (
            <Card key={task.id} className="hover:shadow-lg transition-shadow">
              <CardBody className="p-6">
                <div className="flex justify-between items-start mb-3">
                  <h3 className="text-lg font-semibold line-clamp-2">{task.title}</h3>
                  <Chip color={getUrgencyColor(task.urgency)} variant="flat" size="sm">
                    {task.urgency}
                  </Chip>
                </div>

                <p className="text-gray-600 text-sm mb-4 line-clamp-3">{task.description}</p>

                <div className="space-y-2 mb-4">
                  <div className="flex items-center gap-2 text-sm text-gray-500">
                    <MapPin className="w-4 h-4" />
                    <span>{task.location}</span>
                  </div>
                  <div className="flex items-center gap-2 text-sm text-gray-500">
                    <User className="w-4 h-4" />
                    <span>Posted by {task.postedBy}</span>
                  </div>
                  <div className="flex items-center gap-2 text-sm text-gray-500">
                    <Clock className="w-4 h-4" />
                    <span>{new Date(task.postedAt).toLocaleDateString()}</span>
                  </div>
                </div>

                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-1">
                    <DollarSign className="w-5 h-5 text-green-600" />
                    <span className="text-xl font-bold text-green-600">${task.budget}</span>
                  </div>

                  {user?.role === "ERRAND_BOY" ? (
                    <Button color="primary" size="sm" onClick={() => handleApplyForTask(task.id)}>
                      Apply
                    </Button>
                  ) : (
                    <Button variant="flat" size="sm" isDisabled>
                      View Details
                    </Button>
                  )}
                </div>
              </CardBody>
            </Card>
          ))}
        </div>

        {/* Pagination */}
        {totalPages > 1 && (
          <div className="flex justify-center">
            <Pagination total={totalPages} page={currentPage} onChange={setCurrentPage} color="primary" />
          </div>
        )}
      </div>
    </div>
  )
}
